package ru.otus.libraryapplication.dao.book;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.libraryapplication.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static ru.otus.libraryapplication.LibraryUnitTestData.*;

@JdbcTest
@DisplayName("DAO для работы с книгами должно ")
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc dao;

    @DisplayName("возвращать все книги из БД")
    @Test
    void shouldReturnAllBooks() {
        List<Book> expectedBooks = List.of(BOOK1, BOOK2, BOOK3);
        List<Book> actualBooks = dao.getAll();
        assertThat(actualBooks).usingRecursiveComparison().isEqualTo(expectedBooks);
    }

    @DisplayName("возвращать по ИД книгу из БД, если она там существует")
    @Test
    void shouldReturnExistBookById() {
        Book actualBook = dao.getById(1);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(BOOK1);
    }

    @DisplayName("возвращать по ИД книги null из БД, если ее там не существует")
    @Test
    void shouldReturnNullForNonExistBookById() {
        Book actualBook = dao.getById(4);
        assertThat(actualBook).isNull();
    }

    @DisplayName("удалять по ИД книгу из БД, если она там существует")
    @Test
    void shouldCorrectDeleteBookById() {
        dao.deleteById(2);
        assertThatCode(() -> dao.getById(2)).isNull();
    }

    @DisplayName("не генерировать ошибки при удалении по ИД книги из БД, если ее там не существует")
    @Test
    void shouldDoesNotThrowExceptionsWhereDeleteNonExistBookById() {
        assertThatCode(() -> dao.deleteById(4)).doesNotThrowAnyException();
    }

    @DisplayName("возвращать true, если такая книга уже есть в БД")
    @Test
    void shouldReturnTrueIfEqualBookExist() {
        boolean actual = dao.existByBookAuthorAndGenreNames("EVGENII ONEGIN", "PUSHKIN", "POEM");
        assertThat(actual).isTrue();
    }

    @DisplayName("возвращать false, если такой книги нет в БД")
    @Test
    void shouldReturnFalseIfEqualBookNotExist() {
        boolean actual = dao.existByBookAuthorAndGenreNames("EVGENII ONEGIN", "LERMONTOV", "POEM");
        assertThat(actual).isFalse();
    }

    @DisplayName("записывать новую книгу в БД")
    @Test
    void createNewBook() {
        long actualBookId = dao.create(new Book(null, "MEDNII VSADNIK", AUTHOR1, GENRE1));
        Book actualBook = dao.getById(actualBookId);
        Book expectedBook = new Book(4L, "MEDNII VSADNIK", AUTHOR1, GENRE1);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("обновлять книгу в БД, если она там существует")
    @Test
    void updateExistingBook() {
        Book newBook = new Book(2L, "ANNE OF GREEN GABLES", AUTHOR1, GENRE2);
        dao.update(newBook);
        Book actualBook = dao.getById(2);
        Book expectedBook = new Book(2L, "ANNE OF GREEN GABLES", AUTHOR1, GENRE2);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("не генерировать ошибки при обновлении книги в БД, если ее там не существует")
    @Test
    void updateNonExistAuthor() {
        Book newBook = new Book(4L, "ANNE OF GREEN GABLES", AUTHOR1, GENRE2);
        assertThatCode(() -> dao.update(newBook)).doesNotThrowAnyException();
    }
}