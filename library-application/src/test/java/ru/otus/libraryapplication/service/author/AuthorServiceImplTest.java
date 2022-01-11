package ru.otus.libraryapplication.service.author;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.libraryapplication.dao.author.AuthorDao;
import ru.otus.libraryapplication.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.libraryapplication.LibraryUnitTestData.AUTHOR1;
import static ru.otus.libraryapplication.LibraryUnitTestData.AUTHOR2;

@SpringBootTest
@DisplayName("Сервис для работы с авторами должен ")
class AuthorServiceImplTest {

    @Autowired
    private AuthorServiceImpl authorService;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private AuthorDao genreDao;

    @Test
    @DisplayName("корректно возвращать список всех авторов")
    void shouldCorrectGetAllAuthors() {
        List<Author> expectedAuthors = List.of(AUTHOR1, AUTHOR2);
        Mockito.when(authorDao.getAll()).thenReturn(expectedAuthors);
        List<Author> actualAuthors = authorService.getAll();
        assertThat(actualAuthors).usingRecursiveComparison().isEqualTo(expectedAuthors);
    }

    @Test
    @DisplayName("корректно возвращать автора по ИД")
    void shouldCorrectGetAuthorById() {
        Mockito.when(authorDao.getById(1)).thenReturn(AUTHOR1);
        Author actualAuthor = authorService.getById(1);
        assertThat(actualAuthor).isEqualTo(AUTHOR1);
    }

    @Test
    @DisplayName("корректно возвращать автора названию")
    void shouldCorrectGetAuthorByName() {
        Mockito.when(authorDao.getByName("PUSHKIN")).thenReturn(AUTHOR1);
        Author actualAuthor = authorService.getByName("PUSHKIN");
        assertThat(actualAuthor).isEqualTo(AUTHOR1);
    }

    @Test
    @DisplayName("корректно удалять автора по ИД")
    void shouldCorrectDeleteAuthorById() {
        authorService.deleteById(1);
        Mockito.verify(authorDao, Mockito.times(1)).deleteById(1);
    }

    @Test
    @DisplayName("корректно обновлять автора")
    void shouldCorrectUpdateAuthor() {
        authorService.update(1, "LERMONTOV");
        Mockito.verify(authorDao, Mockito.times(1)).update(1, "LERMONTOV");
    }

    @Test
    @DisplayName("корректно создавать автора")
    void shouldCorrectCreateAuthor() {
        authorService.create("LERMONTOV");
        Mockito.verify(authorDao, Mockito.times(1)).create("LERMONTOV");
    }
}