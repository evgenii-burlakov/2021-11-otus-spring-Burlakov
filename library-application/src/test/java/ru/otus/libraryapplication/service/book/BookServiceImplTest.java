package ru.otus.libraryapplication.service.book;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.repositories.book.BookRepositoryJpa;
import ru.otus.libraryapplication.service.author.AuthorService;
import ru.otus.libraryapplication.service.comment.CommentService;
import ru.otus.libraryapplication.service.genre.GenreService;
import ru.otus.libraryapplication.service.string.StringService;
import ru.otus.libraryapplication.util.exeption.ApplicationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static ru.otus.libraryapplication.LibraryUnitTestData.*;

@SpringBootTest
@DisplayName("Сервис для работы с книгами должен ")
class BookServiceImplTest {
    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private StringService stringService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private BookRepositoryJpa bookRepositoryJpa;

    @Test
    @DisplayName("корректно возвращать список всех книг")
    void shouldCorrectGetAllBooks() {
        List<Book> expectedBooks = List.of(BOOK1, BOOK2);
        Mockito.when(bookRepositoryJpa.getAll()).thenReturn(expectedBooks);
        List<Book> actualBooks = bookService.getAll();
        assertThat(actualBooks).usingRecursiveComparison().isEqualTo(expectedBooks);
    }

    @Test
    @DisplayName("корректно возвращать книгу по ИД")
    void shouldCorrectGetBookById() {
        Mockito.when(bookRepositoryJpa.getById(1)).thenReturn(BOOK1);
        Book actualBook = bookService.getById(1);
        assertThat(actualBook).isEqualTo(BOOK1);
    }

    @Test
    @DisplayName("корректно удалять книгу по ИД")
    void shouldCorrectDeleteBookAndUnusedAuthorAndGenreById() {
        bookService.deleteById(1);
        Mockito.verify(bookRepositoryJpa, Mockito.times(1)).deleteById(1);
    }

    @Test
    @DisplayName("не обновлять книгу на некорректное значение")
    void dontUpdateNotCorrectBook() {
        Mockito.when(stringService.beautifyStringName("EVGENII ONEGIN")).thenReturn("EVGENII ONEGIN");
        Mockito.when(stringService.beautifyStringName("")).thenReturn("");
        Mockito.when(stringService.beautifyStringName("POEM")).thenReturn("POEM");
        Mockito.when(stringService.verifyNotBlank("EVGENII ONEGIN", "", "POEM")).thenReturn(false);
        Mockito.when(bookRepositoryJpa.getById(1)).thenReturn(BOOK1);

        assertThatThrownBy(() -> bookService.update(1, "EVGENII ONEGIN", "", "POEM")).isInstanceOf(ApplicationException.class);

        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("EVGENII ONEGIN");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("POEM");

        Mockito.verify(authorService, Mockito.never()).create(Mockito.anyString());
        Mockito.verify(genreService, Mockito.never()).create(Mockito.anyString());

        Mockito.verify(bookRepositoryJpa, Mockito.never()).create(Mockito.any());
    }

    @Test
    @DisplayName("не обновлять не существующую в БД книгу")
    void dontUpdateNotExistBook() {
        Mockito.when(stringService.beautifyStringName("EVGENII ONEGIN")).thenReturn("EVGENII ONEGIN");
        Mockito.when(stringService.beautifyStringName("LERMONTOV")).thenReturn("LERMONTOV");
        Mockito.when(stringService.beautifyStringName("POEM")).thenReturn("POEM");
        Mockito.when(stringService.verifyNotBlank("EVGENII ONEGIN", "LERMONTOV", "POEM")).thenReturn(true);
        Mockito.when(bookRepositoryJpa.getById(5)).thenReturn(null);

        assertThatThrownBy(() -> bookService.update(5, "EVGENII ONEGIN", "LERMONTOV", "POEM")).isInstanceOf(ApplicationException.class);

        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("EVGENII ONEGIN");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("LERMONTOV");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("POEM");

        Mockito.verify(authorService, Mockito.never()).create(Mockito.anyString());
        Mockito.verify(genreService, Mockito.never()).create(Mockito.anyString());

        Mockito.verify(bookRepositoryJpa, Mockito.never()).create(Mockito.any());
    }

    @Test
    @DisplayName("обновлять книгу, создавать нового автора, если его не было в БД, обновлять жанр, но не создавать новый, если он уже есть в БД")
    void correctUpdateBookAndAddNewAuthorNotDeleteOldAuthorGetNewGenreDeleteOldGenre() {
        Mockito.when(bookRepositoryJpa.getById(1)).thenReturn(BOOK1);

        Mockito.when(stringService.beautifyStringName("EVGENII ONEGIN")).thenReturn("EVGENII ONEGIN");
        Mockito.when(stringService.beautifyStringName("LERMONTOV")).thenReturn("LERMONTOV");
        Mockito.when(stringService.beautifyStringName("NOVEL")).thenReturn("NOVEL");
        Mockito.when(stringService.verifyNotBlank("EVGENII ONEGIN", "LERMONTOV", "NOVEL")).thenReturn(true);


        Mockito.when(authorService.getByName("LERMONTOV")).thenReturn(null);
        Mockito.when(authorService.create("LERMONTOV")).thenReturn(new Author(3L, "LERMONTOV"));

        Mockito.when(genreService.getByName("NOVEL")).thenReturn(GENRE2);

        bookService.update(1, "EVGENII ONEGIN", "LERMONTOV", "NOVEL");

        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("EVGENII ONEGIN");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("LERMONTOV");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("NOVEL");

        Mockito.verify(authorService, Mockito.times(1)).create("LERMONTOV");
        Mockito.verify(genreService, Mockito.never()).create(Mockito.anyString());

        Mockito.verify(bookRepositoryJpa, Mockito.times(1)).create(new Book(1L, "EVGENII ONEGIN", new Author(3L, "LERMONTOV"), GENRE2));
    }

    @Test
    @DisplayName("корректно добавлять книгу, и ее автора, если таких нет в БД, при не нахождении книги в бд")
    void correctCreateBookAndAuthor() {
        Mockito.when(bookRepositoryJpa.existByBookAuthorAndGenreNames("EVGENII ONEGIN", "LERMONTOV", "NOVEL")).thenReturn(false);
        Mockito.when(stringService.beautifyStringName("EVGENII ONEGIN")).thenReturn("EVGENII ONEGIN");
        Mockito.when(stringService.beautifyStringName("LERMONTOV")).thenReturn("LERMONTOV");
        Mockito.when(stringService.beautifyStringName("NOVEL")).thenReturn("NOVEL");
        Mockito.when(stringService.verifyNotBlank("EVGENII ONEGIN", "LERMONTOV", "NOVEL")).thenReturn(true);

        Mockito.when(authorService.getByName("LERMONTOV")).thenReturn(null);
        Mockito.when(authorService.create("LERMONTOV")).thenReturn(new Author(3L, "LERMONTOV"));

        Mockito.when(genreService.getByName("NOVEL")).thenReturn(GENRE2);

        bookService.create("EVGENII ONEGIN", "LERMONTOV", "NOVEL");

        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("EVGENII ONEGIN");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("LERMONTOV");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("NOVEL");

        Mockito.verify(authorService, Mockito.times(1)).create("LERMONTOV");
        Mockito.verify(genreService, Mockito.never()).create(Mockito.anyString());

        Mockito.verify(bookRepositoryJpa, Mockito.times(1)).create(new Book(null, "EVGENII ONEGIN", new Author(3L, "LERMONTOV"), GENRE2));
    }

    @Test
    @DisplayName("не добавлять книгу, она уже есть в БД")
    void notCreateBook() {
        Mockito.when(bookRepositoryJpa.existByBookAuthorAndGenreNames("EVGENII ONEGIN", "LERMONTOV", "NOVEL")).thenReturn(true);
        Mockito.when(stringService.beautifyStringName("EVGENII ONEGIN")).thenReturn("EVGENII ONEGIN");
        Mockito.when(stringService.beautifyStringName("LERMONTOV")).thenReturn("LERMONTOV");
        Mockito.when(stringService.beautifyStringName("NOVEL")).thenReturn("NOVEL");

        assertThatThrownBy(() -> bookService.create("EVGENII ONEGIN", "LERMONTOV", "NOVEL")).isInstanceOf(ApplicationException.class);

        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("EVGENII ONEGIN");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("LERMONTOV");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("NOVEL");

        Mockito.verify(authorService, Mockito.never()).create(Mockito.anyString());
        Mockito.verify(genreService, Mockito.never()).create(Mockito.anyString());

        Mockito.verify(bookRepositoryJpa, Mockito.never()).create(Mockito.any());
    }

    @Test
    @DisplayName("не добавлять не корректную книгу")
    void dontCreateNotCorrectBook() {
        Mockito.when(stringService.beautifyStringName(null)).thenReturn(null);
        Mockito.when(stringService.beautifyStringName("LERMONTOV")).thenReturn("LERMONTOV");
        Mockito.when(stringService.beautifyStringName("NOVEL")).thenReturn("NOVEL");
        Mockito.when(stringService.verifyNotBlank(null, "LERMONTOV", "NOVEL")).thenReturn(false);
        Mockito.when(bookRepositoryJpa.existByBookAuthorAndGenreNames("EVGENII ONEGIN", "LERMONTOV", "NOVEL")).thenReturn(true);


        assertThatThrownBy(() -> bookService.create(null, "LERMONTOV", "NOVEL")).isInstanceOf(ApplicationException.class);

        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName(null);
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("LERMONTOV");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("NOVEL");

        Mockito.verify(authorService, Mockito.never()).create(Mockito.anyString());
        Mockito.verify(genreService, Mockito.never()).create(Mockito.anyString());

        Mockito.verify(bookRepositoryJpa, Mockito.never()).create(Mockito.any());
    }
}