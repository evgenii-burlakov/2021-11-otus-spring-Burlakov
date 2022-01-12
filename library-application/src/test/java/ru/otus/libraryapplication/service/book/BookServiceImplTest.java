package ru.otus.libraryapplication.service.book;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.libraryapplication.dao.book.BookDao;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.service.author.AuthorService;
import ru.otus.libraryapplication.service.genre.GenreService;
import ru.otus.libraryapplication.service.string.StringService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.libraryapplication.LibraryUnitTestData.*;

@SpringBootTest
@DisplayName("Сервис для работы с книгами должен ")
class BookServiceImplTest {
    @Autowired
    private BookServiceImpl bookService;

    @MockBean
    private StringService stringService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private BookDao bookDao;

    @Test
    @DisplayName("корректно возвращать список всех книг")
    void shouldCorrectGetAllBooks() {
        List<Book> expectedBooks = List.of(BOOK1, BOOK2);
        Mockito.when(bookDao.getAll()).thenReturn(expectedBooks);
        List<Book> actualBooks = bookService.getAll();
        assertThat(actualBooks).usingRecursiveComparison().isEqualTo(expectedBooks);
    }

    @Test
    @DisplayName("корректно возвращать книгу по ИД")
    void shouldCorrectGetBookById() {
        Mockito.when(bookDao.getById(1)).thenReturn(BOOK1);
        Book actualBook = bookService.getById(1);
        assertThat(actualBook).isEqualTo(BOOK1);
    }

    @Test
    @DisplayName("корректно удалять книгу по ИД и удалять автора и жанр, если по ним больше не осталось книг")
    void shouldCorrectDeleteBookAndUnusedAuthorAndGenreById() {
        Mockito.when(bookDao.getById(1)).thenReturn(BOOK1);
        long book1AuthorId = BOOK1.getAuthor().getId();
        Mockito.when(bookDao.countByAuthor(book1AuthorId)).thenReturn(0);
        long book1GenreId = BOOK1.getGenre().getId();
        Mockito.when(bookDao.countByGenre(book1GenreId)).thenReturn(0);
        bookService.deleteById(1);
        Mockito.verify(bookDao, Mockito.times(1)).deleteById(1);
        Mockito.verify(authorService, Mockito.times(1)).deleteById(book1AuthorId);
        Mockito.verify(genreService, Mockito.times(1)).deleteById(book1GenreId);
    }

    @Test
    @DisplayName("корректно удалять книгу по ИД и не удалять автора и жанр, если по ним еще остались книги")
    void shouldCorrectDeleteBookById() {
        Mockito.when(bookDao.getById(1)).thenReturn(BOOK1);
        long book1AuthorId = BOOK1.getAuthor().getId();
        Mockito.when(bookDao.countByAuthor(book1AuthorId)).thenReturn(1);
        long book1GenreId = BOOK1.getGenre().getId();
        Mockito.when(bookDao.countByGenre(book1GenreId)).thenReturn(1);
        bookService.deleteById(1);
        Mockito.verify(bookDao, Mockito.times(1)).deleteById(1);
        Mockito.verify(authorService, Mockito.never()).deleteById(Mockito.anyLong());
        Mockito.verify(genreService, Mockito.never()).deleteById(Mockito.anyLong());
    }

    @Test
    @DisplayName("не обновлять книгу, если ни одно поле не поменялось")
    void dontUpdateUnchangedBook() {
        Mockito.when(bookDao.getById(1)).thenReturn(BOOK1);

        Mockito.when(stringService.beautifyStringName("EVGENII ONEGIN")).thenReturn("EVGENII ONEGIN");
        Mockito.when(stringService.beautifyStringName("PUSHKIN")).thenReturn("PUSHKIN");
        Mockito.when(stringService.beautifyStringName("POEM")).thenReturn("POEM");

        bookService.update(1, "EVGENII ONEGIN", "PUSHKIN", "POEM");

        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("EVGENII ONEGIN");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("PUSHKIN");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("POEM");

        Mockito.verify(authorService, Mockito.never()).create(Mockito.anyString());
        Mockito.verify(genreService, Mockito.never()).create(Mockito.anyString());

        Mockito.verify(bookDao, Mockito.never()).update(Mockito.anyLong(), Mockito.anyString(), Mockito.any(), Mockito.any());
        Mockito.verify(authorService, Mockito.never()).deleteById(Mockito.anyLong());
        Mockito.verify(genreService, Mockito.never()).deleteById(Mockito.anyLong());
    }

    @Test
    @DisplayName("не обновлять книгу на некорректное значение")
    void dontUpdateNotCorrectBook() {
        Mockito.when(stringService.beautifyStringName("EVGENII ONEGIN")).thenReturn("EVGENII ONEGIN");
        Mockito.when(stringService.beautifyStringName("")).thenReturn("");
        Mockito.when(stringService.beautifyStringName("POEM")).thenReturn("POEM");
        Mockito.when(stringService.verifyNotBlank("EVGENII ONEGIN", "", "POEM")).thenReturn(false);
        Mockito.when(bookDao.getById(1)).thenReturn(BOOK1);

        bookService.update(1, "EVGENII ONEGIN", "", "POEM");

        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("EVGENII ONEGIN");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("POEM");

        Mockito.verify(authorService, Mockito.never()).create(Mockito.anyString());
        Mockito.verify(genreService, Mockito.never()).create(Mockito.anyString());

        Mockito.verify(bookDao, Mockito.never()).update(Mockito.anyLong(), Mockito.anyString(), Mockito.any(), Mockito.any());
        Mockito.verify(authorService, Mockito.never()).deleteById(Mockito.anyLong());
        Mockito.verify(genreService, Mockito.never()).deleteById(Mockito.anyLong());
    }

    @Test
    @DisplayName("обновлять книгу, создавать нового автора, если его не было в БД и удалять старого, если это была его единственная книга," +
            "не обновлять жанр, если он не поменялся")
    void correctUpdateBookAndAddNewAuthorAndDeleteOldAuthor() {
        Mockito.when(bookDao.getById(1)).thenReturn(BOOK1);

        Mockito.when(stringService.beautifyStringName("EVGENII ONEGIN")).thenReturn("EVGENII ONEGIN");
        Mockito.when(stringService.beautifyStringName("LERMONTOV")).thenReturn("LERMONTOV");
        Mockito.when(stringService.beautifyStringName("POEM")).thenReturn("POEM");
        Mockito.when(stringService.verifyNotBlank("EVGENII ONEGIN", "LERMONTOV", "POEM")).thenReturn(true);

        Mockito.when(bookDao.countByAuthor(1)).thenReturn(1);
        Mockito.when(authorService.getByName("LERMONTOV")).thenReturn(null);
        Mockito.when(authorService.create("LERMONTOV")).thenReturn(3L);
        bookService.update(1, "EVGENII ONEGIN", "LERMONTOV", "POEM");

        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("EVGENII ONEGIN");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("LERMONTOV");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("POEM");

        Mockito.verify(authorService, Mockito.times(1)).create("LERMONTOV");
        Mockito.verify(genreService, Mockito.never()).create(Mockito.anyString());

        Mockito.verify(bookDao, Mockito.times(1)).update(1, "EVGENII ONEGIN", new Author(3, "LERMONTOV"), GENRE1);
        Mockito.verify(authorService, Mockito.times(1)).deleteById(1);
        Mockito.verify(genreService, Mockito.never()).deleteById(Mockito.anyLong());
    }

    @Test
    @DisplayName("обновлять книгу, создавать нового автора, если его не было в БД и не удалять старого, если это была его не единственная книга," +
            "обновлять жанр, но не создавать новый, если он уже есть в БД, удалять старый, если он был единственный")
    void correctUpdateBookAndAddNewAuthorNotDeleteOldAuthorGetNewGenreDeleteOldGenre() {
        Mockito.when(bookDao.getById(1)).thenReturn(BOOK1);

        Mockito.when(stringService.beautifyStringName("EVGENII ONEGIN")).thenReturn("EVGENII ONEGIN");
        Mockito.when(stringService.beautifyStringName("LERMONTOV")).thenReturn("LERMONTOV");
        Mockito.when(stringService.beautifyStringName("NOVEL")).thenReturn("NOVEL");
        Mockito.when(stringService.verifyNotBlank("EVGENII ONEGIN", "LERMONTOV", "NOVEL")).thenReturn(true);

        Mockito.when(bookDao.countByAuthor(1)).thenReturn(2);
        Mockito.when(authorService.getByName("LERMONTOV")).thenReturn(null);
        Mockito.when(authorService.create("LERMONTOV")).thenReturn(3L);

        Mockito.when(bookDao.countByGenre(1)).thenReturn(1);
        Mockito.when(genreService.getByName("NOVEL")).thenReturn(GENRE2);

        bookService.update(1, "EVGENII ONEGIN", "LERMONTOV", "NOVEL");

        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("EVGENII ONEGIN");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("LERMONTOV");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("NOVEL");

        Mockito.verify(authorService, Mockito.times(1)).create("LERMONTOV");
        Mockito.verify(genreService, Mockito.never()).create(Mockito.anyString());

        Mockito.verify(bookDao, Mockito.times(1)).update(1, "EVGENII ONEGIN", new Author(3, "LERMONTOV"), GENRE2);
        Mockito.verify(authorService, Mockito.never()).deleteById(Mockito.anyLong());
        Mockito.verify(genreService, Mockito.times(1)).deleteById(1);
    }

    @Test
    @DisplayName("корректно добавлять книгу, и ее автора, если таких нет в БД, при не нахождении книги в бд")
    void correctCreateBookAndAuthor() {
        Mockito.when(bookDao.isEqualBookExist("EVGENII ONEGIN", "LERMONTOV", "NOVEL")).thenReturn(false);
        Mockito.when(stringService.beautifyStringName("EVGENII ONEGIN")).thenReturn("EVGENII ONEGIN");
        Mockito.when(stringService.beautifyStringName("LERMONTOV")).thenReturn("LERMONTOV");
        Mockito.when(stringService.beautifyStringName("NOVEL")).thenReturn("NOVEL");
        Mockito.when(stringService.verifyNotBlank("EVGENII ONEGIN", "LERMONTOV", "NOVEL")).thenReturn(true);

        Mockito.when(authorService.getByName("LERMONTOV")).thenReturn(null);
        Mockito.when(authorService.create("LERMONTOV")).thenReturn(3L);

        Mockito.when(genreService.getByName("NOVEL")).thenReturn(GENRE2);

        bookService.create("EVGENII ONEGIN", "LERMONTOV", "NOVEL");

        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("EVGENII ONEGIN");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("LERMONTOV");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("NOVEL");

        Mockito.verify(authorService, Mockito.times(1)).create("LERMONTOV");
        Mockito.verify(genreService, Mockito.never()).create(Mockito.anyString());

        Mockito.verify(bookDao, Mockito.times(1)).create("EVGENII ONEGIN", new Author(3, "LERMONTOV"), GENRE2);
    }

    @Test
    @DisplayName("не добавлять книгу, она уже есть в БД")
    void dontCreateBook() {
        Mockito.when(bookDao.isEqualBookExist("EVGENII ONEGIN", "LERMONTOV", "NOVEL")).thenReturn(true);
        Mockito.when(stringService.beautifyStringName("EVGENII ONEGIN")).thenReturn("EVGENII ONEGIN");
        Mockito.when(stringService.beautifyStringName("LERMONTOV")).thenReturn("LERMONTOV");
        Mockito.when(stringService.beautifyStringName("NOVEL")).thenReturn("NOVEL");

        bookService.create("EVGENII ONEGIN", "LERMONTOV", "NOVEL");

        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("EVGENII ONEGIN");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("LERMONTOV");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("NOVEL");

        Mockito.verify(authorService, Mockito.never()).create(Mockito.anyString());
        Mockito.verify(genreService, Mockito.never()).create(Mockito.anyString());

        Mockito.verify(bookDao, Mockito.never()).create(Mockito.anyString(), Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("не добавлять не корректную книгу")
    void dontCreateNotCorrectBook() {
        Mockito.when(stringService.beautifyStringName(null)).thenReturn(null);
        Mockito.when(stringService.beautifyStringName("LERMONTOV")).thenReturn("LERMONTOV");
        Mockito.when(stringService.beautifyStringName("NOVEL")).thenReturn("NOVEL");
        Mockito.when(stringService.verifyNotBlank(null, "LERMONTOV", "NOVEL")).thenReturn(false);
        Mockito.when(bookDao.isEqualBookExist("EVGENII ONEGIN", "LERMONTOV", "NOVEL")).thenReturn(true);

        bookService.create(null, "LERMONTOV", "NOVEL");

        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName(null);
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("LERMONTOV");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("NOVEL");

        Mockito.verify(authorService, Mockito.never()).create(Mockito.anyString());
        Mockito.verify(genreService, Mockito.never()).create(Mockito.anyString());

        Mockito.verify(bookDao, Mockito.never()).create(Mockito.anyString(), Mockito.any(), Mockito.any());
    }
}