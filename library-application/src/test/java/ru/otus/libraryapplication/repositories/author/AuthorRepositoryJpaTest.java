package ru.otus.libraryapplication.repositories.author;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.libraryapplication.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static ru.otus.libraryapplication.LibraryUnitTestData.AUTHOR1;
import static ru.otus.libraryapplication.LibraryUnitTestData.AUTHOR2;

@DataJpaTest
@DisplayName("DAO для работы с авторами должно ")
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    @DisplayName("возвращать всех авторов из БД")
    @Test
    void shouldReturnAllAuthors() {
        List<Author> expectedAuthors = List.of(AUTHOR1, AUTHOR2);
        List<Author> actualAuthors = authorRepositoryJpa.getAll();
        assertThat(actualAuthors).usingRecursiveComparison().isEqualTo(expectedAuthors);
    }

    @DisplayName("возвращать по ИД автора из БД, если он там существует")
    @Test
    void shouldReturnExistAuthorById() {
        Author actualAuthor = authorRepositoryJpa.getById(1);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(AUTHOR1);
    }

    @DisplayName("возвращать по ИД автора null из БД, если он там не существует")
    @Test
    void shouldReturnNullForNonExistAuthorById() {
        Author actualAuthor = authorRepositoryJpa.getById(3);
        assertThat(actualAuthor).isNull();
    }

    @DisplayName("возвращать по названию автора из БД, если он там существует")
    @Test
    void shouldReturnExistAuthorByName() {
        Author actualAuthor = authorRepositoryJpa.getByName("PUSHKIN");
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(AUTHOR1);
    }

    @DisplayName("возвращать по названию автора null из БД, если он там не существует")
    @Test
    void shouldReturnNullForNonExistAuthorByName() {
        Author actualAuthor = authorRepositoryJpa.getByName("LERMONTOV");
        assertThat(actualAuthor).isNull();
    }

    @DisplayName("удалять по ИД автора из БД, если он там существует")
    @Test
    void shouldCorrectDeleteAuthorById() {
        authorRepositoryJpa.deleteById(2);
        assertThatCode(() -> authorRepositoryJpa.getById(2)).isNull();
    }

    @DisplayName("не генерировать ошибки при удалении по ИД автора из БД, если его там не существует")
    @Test
    void shouldDoesNotThrowExceptionsWhereDeleteNonExistAuthorById() {
        assertThatCode(() -> authorRepositoryJpa.deleteById(3)).doesNotThrowAnyException();
    }

    @DisplayName("обновлять автора в БД, если он там существует")
    @Test
    void updateExistingAuthor() {
        Author newAuthor = new Author(2L, "WES MONTGOMERY");
        authorRepositoryJpa.update(newAuthor);
        Author actualAuthor = authorRepositoryJpa.getById(2);
        Author expectedAuthor = new Author(2L, "WES MONTGOMERY");
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @DisplayName("не генерировать ошибки при обновлении автора в БД, если он там не существует")
    @Test
    void updateNonExistAuthor() {
        Author newAuthor = new Author(3L, "WES MONTGOMERY");
        assertThatCode(() -> authorRepositoryJpa.update(newAuthor)).doesNotThrowAnyException();
    }

    @DisplayName("записывать нового автора в БД")
    @Test
    void createNewAuthor() {
        Author actualAuthor = authorRepositoryJpa.create(new Author(null, "Pallanik"));
        Author expectedAuthor = new Author(3L, "Pallanik");
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }
}