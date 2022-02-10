package ru.otus.libraryapplication.controller.genre;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.libraryapplication.dto.GenreDto;
import ru.otus.libraryapplication.service.genre.GenreService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.libraryapplication.LibraryUnitTestData.GENRE1;
import static ru.otus.libraryapplication.LibraryUnitTestData.GENRE2;

@WebMvcTest(GenreController.class)
@DisplayName("Контроллер для работы с жанрами должен ")
class GenreControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @Test
    @DisplayName("корректно возвращать все жанры")
    void correctGetAllGenres() throws Exception {
        given(genreService.getAll()).willReturn(List.of(GENRE1, GENRE2));

        List<GenreDto> expectedResult = Stream.of(GENRE1, GENRE2)
                .map(GenreDto::toDto).collect(Collectors.toList());

        mvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(view().name("genres"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(model().attribute("genres", expectedResult));
    }

    @Test
    @DisplayName("корректно удалять жанр")
    void correctDeleteGenreById() throws Exception {
        mvc.perform(get("/genres/delete?id=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/genres"));
        Mockito.verify(genreService, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("корректно возвращать страницу редактирования автора")
    void correctReturnEditPage() throws Exception {
        given(genreService.getById(1L)).willReturn(GENRE1);
        GenreDto expectedResult = GenreDto.toDto(GENRE1);

        mvc.perform(get("/genres/edit?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editGenre"))
                .andExpect(model().attributeExists("genre"))
                .andExpect(model().attribute("genre", expectedResult));
    }

    @Test
    @DisplayName("корректно редактировать жанр")
    void correctUpdateGenre() throws Exception {
        mvc.perform(post("/genres/edit")
                        .param("id", "1")
                        .param("name", "Poem"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/genres"));
        Mockito.verify(genreService, times(1)).update(1, "Poem");
    }

    @Test
    @DisplayName("корректно возвращать страницу создания жанра")
    void correctReturnCreatePage() throws Exception {
        mvc.perform(get("/genres/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createGenre"));
    }

    @Test
    @DisplayName("корректно создавать жанр")
    void correctCreateGenre() throws Exception {
        mvc.perform(post("/genres/create")
                        .param("name", "Poem"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/genres"));
        Mockito.verify(genreService, times(1)).create("Poem");
    }
}