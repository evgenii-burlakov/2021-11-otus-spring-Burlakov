package ru.otus.libraryapplication.dao.book;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.libraryapplication.dao.author.AuthorDaoJdbc;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@DisplayName("DAO для работы с книгами должно ")
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {
    private static final Author AUTHOR1 = new Author(1, "PUSHKIN");
    private static final Author AUTHOR2 = new Author(2, "MONTGOMERY");

    private static final Genre GENRE1 = new Genre(1, "POEM");
    private static final Genre GENRE2 = new Genre(2, "NOVEL");

    private static final Book BOOK1 = new Book(1, "EVGENII ONEGIN", AUTHOR1, GENRE1);
    private static final Book BOOK2 = new Book(2, "ANNE OF GREEN GABLES", AUTHOR2, GENRE2);
    private static final Book BOOK3 = new Book(3, "ANNE OF GREEN GABLES POEM EDITION", AUTHOR2, GENRE1);

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

    @DisplayName("записывать новую книгу в БД")
    @Test
    void createNewBook() {
        long actualBookId = dao.create("MEDNII VSADNIK", AUTHOR1, GENRE1);
        Book actualBook = dao.getById(actualBookId);
        Book expectedBook = new Book(4, "MEDNII VSADNIK", AUTHOR1, GENRE1);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("обновлять книгу в БД, если она там существует")
    @Test
    void updateExistingBook() {
        dao.update(2, "ANNE OF GREEN GABLES", AUTHOR1, GENRE2);
        Book actualBook = dao.getById(2);
        Book expectedBook = new Book(2, "ANNE OF GREEN GABLES", AUTHOR1, GENRE2);
        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("не генерировать ошибки при обновлении книги в БД, если ее там не существует")
    @Test
    void updateNonExistAuthor() {
        assertThatCode(() -> dao.update(4, "ANNE OF GREEN GABLES", AUTHOR1, GENRE2)).doesNotThrowAnyException();
    }

    @DisplayName("корректно считать количество книг автора из БД, если они там существуют")
    @Test
    void countExistingBookByAuthor() {
        int actualCount = dao.countByAuthor(2);
        int expectedCount = 2;
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("корректно считать количество книг автора из БД, если они там не существуют")
    @Test
    void countNonExistingBooksByAuthor() {
        int actualCount = dao.countByAuthor(3);
        int expectedCount = 0;
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("корректно считать количество книг жанра из БД, если они там существуют")
    @Test
    void countExistingBookByGenre() {
        int actualCount = dao.countByGenre(1);
        int expectedCount = 2;
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("корректно считать количество книг жанра из БД, если они там не существуют")
    @Test
    void countNonExistingBooksByGenre() {
        int actualCount = dao.countByGenre(3);
        int expectedCount = 0;
        assertThat(actualCount).isEqualTo(expectedCount);
    }
}