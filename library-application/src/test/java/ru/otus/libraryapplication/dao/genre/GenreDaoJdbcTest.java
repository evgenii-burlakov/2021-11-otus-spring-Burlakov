package ru.otus.libraryapplication.dao.genre;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.libraryapplication.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static ru.otus.libraryapplication.LibraryUnitTestData.*;

@JdbcTest
@DisplayName("DAO для работы с жанрами должно ")
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc dao;

    @DisplayName("возвращать все жанры из БД")
    @Test
    void shouldReturnAllGenres() {
        List<Genre> expectedGenres = List.of(GENRE1, GENRE2);
        List<Genre> actualGenres = dao.getAll();
        assertThat(actualGenres).usingRecursiveComparison().isEqualTo(expectedGenres);
    }

    @DisplayName("возвращать по ИД жанр из БД, если он там существует")
    @Test
    void shouldReturnExistGenreById() {
        Genre actualAuthor = dao.getById(1);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(GENRE1);
    }

    @DisplayName("возвращать по ИД жанра null из БД, если он там не существует")
    @Test
    void shouldReturnNullForNonExistGenreById() {
        Genre actualGenre = dao.getById(3);
        assertThat(actualGenre).isNull();
    }

    @DisplayName("возвращать по названию жанр из БД, если он там существует")
    @Test
    void shouldReturnExistGenreByName() {
        Genre actualGenre = dao.getByName("POEM");
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(GENRE1);
    }

    @DisplayName("возвращать по названию жанра null из БД, если он там не существует")
    @Test
    void shouldReturnNullForNonExistGenreByName() {
        Genre actualGenre = dao.getByName("DETECTIVE");
        assertThat(actualGenre).isNull();
    }

    @DisplayName("удалять по ИД жанр из БД, если он там существует")
    @Test
    void shouldCorrectDeleteGenreById() {
        dao.deleteById(2);
        assertThatCode(() -> dao.getById(2)).isNull();
    }

    @DisplayName("не генерировать ошибки при удалении по ИД жанра из БД, если его там не существует")
    @Test
    void shouldDoesNotThrowExceptionsWhereDeleteNonExistGenreById() {
        assertThatCode(() -> dao.deleteById(3)).doesNotThrowAnyException();
    }

    @DisplayName("обновлять жанр в БД, если он там существует")
    @Test
    void updateExistingGenre() {
        dao.update(2, "DETECTIVE");
        Genre actualGenre = dao.getById(2);
        Genre expectedGenre = new Genre(2, "DETECTIVE");
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @DisplayName("не генерировать ошибки при обновлении жанра в БД, если он там не существует")
    @Test
    void updateNonExistGenre() {
        assertThatCode(() -> dao.update(3, "DETECTIVE")).doesNotThrowAnyException();
    }

    @DisplayName("записывать новый жанр в БД")
    @Test
    void createNewGenre() {
        long actualGenreId = dao.create("DETECTIVE");
        Genre actualGenre = dao.getById(actualGenreId);
        Genre expectedGenre = new Genre(3, "DETECTIVE");
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @DisplayName("отдавать корректный список ИД жанров, для которых данный автор был уникальным, в случае если такие жанры существуют " +
            "(у автора есть уникальные жанры)")
    @Test
    void getExistingGenresToAuthor() {
        List<Long> actualGenresId = dao.getUniqueGenresToAuthor(2);
        List<Long> expectedGenresId = List.of(2L);
        assertThat(actualGenresId).isEqualTo(expectedGenresId);
    }

    @DisplayName("отдавать пустой список ИД жанров, для которых данный автор был уникальным, в случае если такие жанры не существуют " +
            "(у автора нет уникальных жанров, так как в них писали другие авторы)")
    @Test
    void getNonExistGenresToAuthor() {
        List<Long> actualGenresId = dao.getUniqueGenresToAuthor(1);
        List<Long> expectedGenresId = List.of();
        assertThat(actualGenresId).isEqualTo(expectedGenresId);
    }

    @DisplayName("отдавать пустой список ИД жанров, для которых данный автор был уникальным, в случае если автор не писал ни в каком жанре " +
            "(автор не встречается ни у одного жанра)")
    @Test
    void getNonExistGenresToNonExistAuthor() {
        List<Long> actualGenresId = dao.getUniqueGenresToAuthor(3);
        List<Long> expectedGenresId = List.of();
        assertThat(actualGenresId).isEqualTo(expectedGenresId);
    }
}