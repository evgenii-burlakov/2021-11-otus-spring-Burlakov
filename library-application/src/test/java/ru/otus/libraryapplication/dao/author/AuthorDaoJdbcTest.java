package ru.otus.libraryapplication.dao.author;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.libraryapplication.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import static ru.otus.libraryapplication.LibraryUnitTestData.AUTHOR1;
import static ru.otus.libraryapplication.LibraryUnitTestData.AUTHOR2;

@JdbcTest
@DisplayName("DAO для работы с авторами должно ")
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc dao;

    @DisplayName("возвращать всех авторов из БД")
    @Test
    void shouldReturnAllAuthors() {
        List<Author> expectedAuthors = List.of(AUTHOR1, AUTHOR2);
        List<Author> actualAuthors = dao.getAll();
        assertThat(actualAuthors).usingRecursiveComparison().isEqualTo(expectedAuthors);
    }

    @DisplayName("возвращать по ИД автора из БД, если он там существует")
    @Test
    void shouldReturnExistAuthorById() {
        Author actualAuthor = dao.getById(1);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(AUTHOR1);
    }

    @DisplayName("возвращать по ИД автора null из БД, если он там не существует")
    @Test
    void shouldReturnNullForNonExistAuthorById() {
        Author actualAuthor = dao.getById(3);
        assertThat(actualAuthor).isNull();
    }

    @DisplayName("возвращать по названию автора из БД, если он там существует")
    @Test
    void shouldReturnExistAuthorByName() {
        Author actualAuthor = dao.getByName("PUSHKIN");
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(AUTHOR1);
    }

    @DisplayName("возвращать по названию автора null из БД, если он там не существует")
    @Test
    void shouldReturnNullForNonExistAuthorByName() {
        Author actualAuthor = dao.getByName("LERMONTOV");
        assertThat(actualAuthor).isNull();
    }

    @DisplayName("удалять по ИД автора из БД, если он там существует")
    @Test
    void shouldCorrectDeleteAuthorById() {
        dao.deleteById(2);
        assertThatCode(() -> dao.getById(2)).isNull();
    }

    @DisplayName("не генерировать ошибки при удалении по ИД автора из БД, если его там не существует")
    @Test
    void shouldDoesNotThrowExceptionsWhereDeleteNonExistAuthorById() {
        assertThatCode(() -> dao.deleteById(3)).doesNotThrowAnyException();
    }

    @DisplayName("обновлять автора в БД, если он там существует")
    @Test
    void updateExistingAuthor() {
        dao.update(2, "WES MONTGOMERY");
        Author actualAuthor = dao.getById(2);
        Author expectedAuthor = new Author(2, "WES MONTGOMERY");
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @DisplayName("не генерировать ошибки при обновлении автора в БД, если он там не существует")
    @Test
    void updateNonExistAuthor() {
        assertThatCode(() -> dao.update(3, "WES MONTGOMERY")).doesNotThrowAnyException();
    }

    @DisplayName("записывать нового автора в БД")
    @Test
    void createNewAuthor() {
        long actualAuthorId = dao.create("Pallanik");
        Author actualAuthor = dao.getById(actualAuthorId);
        Author expectedAuthor = new Author(3, "Pallanik");
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @DisplayName("отдавать корректный список ИД авторов, для которых данный жанр был уникальным, в случае если такие авторы существуют " +
            "(у жанра есть уникальные авторы)")
    @Test
    void getExistingAuthorsToGenre() {
        List<Long> actualAuthorsId = dao.getUniqueAuthorsToGenre(1);
        List<Long> expectedAuthorsId = List.of(1L);
        assertThat(actualAuthorsId).isEqualTo(expectedAuthorsId);
    }

    @DisplayName("отдавать пустой список ИД авторов, для которых данный жанр был уникальным, в случае если такие авторы не существуют " +
            "(у жанра нет уникальных авторов, так как они писали (и) в других жанрах)")
    @Test
    void getNonExistAuthorsToGenre() {
        List<Long> actualAuthorsId = dao.getUniqueAuthorsToGenre(2);
        List<Long> expectedAuthorsId = List.of();
        assertThat(actualAuthorsId).isEqualTo(expectedAuthorsId);
    }

    @DisplayName("отдавать пустой список ИД авторов, для которых данный жанр был уникальным, в случае если в таком жанре не писал никто " +
            "(жанр не встречается ни у одного писателя)")
    @Test
    void getNonExistAuthorsToNonExistGenre() {
        List<Long> actualAuthorsId = dao.getUniqueAuthorsToGenre(3);
        List<Long> expectedAuthorsId = List.of();
        assertThat(actualAuthorsId).isEqualTo(expectedAuthorsId);
    }
}