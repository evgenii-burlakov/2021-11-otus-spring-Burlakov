package ru.otus.libraryapplication.controller.author;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.dto.AuthorDto;
import ru.otus.libraryapplication.service.author.AuthorService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.libraryapplication.LibraryUnitTestData.AUTHOR1;
import static ru.otus.libraryapplication.LibraryUnitTestData.AUTHOR2;

@WebMvcTest(AuthorController.class)
@DisplayName("Контроллер для работы с авторами должен ")
class AuthorControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Test
    @DisplayName("корректно возвращать всех авторов")
    void correctGetAllAuthors() throws Exception {
        Mockito.when(authorService.getAll()).thenReturn(List.of(AUTHOR1, AUTHOR2));

        mvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("PUSHKIN")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("MONTGOMERY")));
    }

    @Test
    @DisplayName("корректно удалять автора")
    void correctDeleteAuthorById() throws Exception {
        mvc.perform(delete("/authors")
                        .param("id", "1"))
                .andExpect(status().isOk());

        Mockito.verify(authorService, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("корректно редактировать автора")
    void correctUpdateAuthor() throws Exception {
        mvc.perform(patch("/authors/1")
                        .param("id", "1")
                        .param("name", "Pushkin"))
                .andExpect(status().isOk());

        Mockito.verify(authorService, times(1)).update(1, "Pushkin");
    }

    @Test
    @DisplayName("корректно возвращать автора по ИД")
    void correctReturnAuthorById() throws Exception {
        given(authorService.getById(1)).willReturn(AUTHOR2);

        mvc.perform(get("/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(2)))
                .andExpect(jsonPath("name", is("MONTGOMERY")));
    }

    @Test
    @DisplayName("корректно создавать автора")
    void correctCreateAuthor() throws Exception {
        mvc.perform(post("/authors")
                        .param("name", "Pushkin"))
                .andExpect(status().isOk());
        Mockito.verify(authorService, times(1)).create("Pushkin");
    }
}