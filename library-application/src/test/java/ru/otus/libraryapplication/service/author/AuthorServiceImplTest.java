package ru.otus.libraryapplication.service.author;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.libraryapplication.dao.author.AuthorDao;
import ru.otus.libraryapplication.dao.genre.GenreDao;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.service.string.StringService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.libraryapplication.LibraryUnitTestData.AUTHOR1;
import static ru.otus.libraryapplication.LibraryUnitTestData.AUTHOR2;

@SpringBootTest
@DisplayName("Сервис для работы с авторами должен ")
class AuthorServiceImplTest {

    @Autowired
    private AuthorServiceImpl authorService;

    @MockBean
    private StringService stringService;

    @MockBean
    private AuthorDao authorDao;

    @MockBean
    private GenreDao genreDao;

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
    @DisplayName("корректно удалять автора по ИД и удалять неиспользуемые жанры, если такие имеются")
    void shouldCorrectDeleteAuthorAndUnusedGenresById() {
        Mockito.when(genreDao.getUniqueGenresToAuthor(2)).thenReturn(List.of(3L, 4L));
        authorService.deleteById(2);
        Mockito.verify(genreDao, Mockito.times(1)).getUniqueGenresToAuthor(2);
        Mockito.verify(genreDao, Mockito.times(1)).deleteById(3);
        Mockito.verify(genreDao, Mockito.times(1)).deleteById(4);
        Mockito.verify(authorDao, Mockito.times(1)).deleteById(2);
    }

    @Test
    @DisplayName("корректно удалять автора по ИД и не удалять неиспользуемые жанры, если таких нет")
    void shouldCorrectDeleteAuthorById() {
        Mockito.when(genreDao.getUniqueGenresToAuthor(2)).thenReturn(List.of());
        authorService.deleteById(2);
        Mockito.verify(genreDao, Mockito.times(1)).getUniqueGenresToAuthor(2);
        Mockito.verify(genreDao, Mockito.never()).deleteById(Mockito.anyLong());
        Mockito.verify(authorDao, Mockito.times(1)).deleteById(2);
    }

    @Test
    @DisplayName("корректно обновлять автора")
    void shouldCorrectUpdateAuthor() {
        Mockito.when(stringService.beautifyStringName("LERMONTOV")).thenReturn("LERMONTOV");
        Mockito.when(stringService.verifyNotBlank("LERMONTOV")).thenReturn(true);
        authorService.update(1, "LERMONTOV");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("LERMONTOV");
        Mockito.verify(authorDao, Mockito.times(1)).update(1, "LERMONTOV");
    }

    @Test
    @DisplayName("не обновлять автора при не валидном наименовании")
    void shouldNotUpdateBlankAuthorName() {
        Mockito.when(stringService.beautifyStringName("  ")).thenReturn("");
        Mockito.when(stringService.verifyNotBlank("")).thenReturn(false);
        authorService.update(1, "  ");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("  ");
        Mockito.verify(stringService, Mockito.times(1)).verifyNotBlank("");
        Mockito.verify(authorDao, Mockito.never()).update(Mockito.anyLong(), Mockito.anyString());
    }

    @Test
    @DisplayName("корректно создавать автора")
    void shouldCorrectCreateAuthor() {
        Mockito.when(stringService.beautifyStringName("LERMONTOV")).thenReturn("LERMONTOV");
        Mockito.when(stringService.verifyNotBlank("LERMONTOV")).thenReturn(true);
        authorService.create("LERMONTOV");
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName("LERMONTOV");
        Mockito.verify(authorDao, Mockito.times(1)).create("LERMONTOV");
    }

    @Test
    @DisplayName("не создавать не валидного автора")
    void shouldNotCreateNullAuthor() {
        Mockito.when(stringService.beautifyStringName(null)).thenReturn(null);
        Mockito.when(stringService.verifyNotBlank((String) null)).thenReturn(false);
        authorService.create(null);
        Mockito.verify(stringService, Mockito.times(1)).beautifyStringName(null);
        Mockito.verify(authorDao, Mockito.never()).create(null);
    }
}