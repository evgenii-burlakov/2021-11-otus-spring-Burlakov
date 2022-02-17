package ru.otus.libraryapplication.controller.book;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.libraryapplication.service.book.BookService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.otus.libraryapplication.LibraryUnitTestData.BOOK1;
import static ru.otus.libraryapplication.LibraryUnitTestData.BOOK2;

@WebMvcTest(BookController.class)
@DisplayName("Контроллер для работы с книгами должен ")
class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("корректно возвращать все книги")
    void correctGetAllBooks() throws Exception {
        given(bookService.getAll()).willReturn(List.of(BOOK1, BOOK2));

        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("EVGENII ONEGIN")))
                .andExpect(jsonPath("$[0].author.id", is(1)))
                .andExpect(jsonPath("$[0].genre.id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("ANNE OF GREEN GABLES")))
                .andExpect(jsonPath("$[1].author.id", is(2)))
                .andExpect(jsonPath("$[1].genre.id", is(2)));
    }

    @Test
    @DisplayName("корректно удалять книгу")
    void correctDeleteBookById() throws Exception {
        mvc.perform(delete("/books")
                        .param("id", "1"))
                .andExpect(status().isOk());

        Mockito.verify(bookService, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("корректно возвращать книгу по ИД")
    void correctReturnGenreById() throws Exception {
        given(bookService.getById(1)).willReturn(BOOK1);

        mvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("EVGENII ONEGIN")))
                .andExpect(jsonPath("author.id", is(1)))
                .andExpect(jsonPath("genre.id", is(1)));
    }

    @Test
    @DisplayName("корректно редактировать книгу")
    void correctUpdateBook() throws Exception {
        mvc.perform(patch("/books/1")
                        .param("id", "1")
                        .param("name", "We")
                        .param("author.name", "Zamiatin")
                        .param("genre.name", "Fantasy"))
                .andExpect(status().isOk());

        Mockito.verify(bookService, times(1)).update(1, "We", "Zamiatin", "Fantasy");
    }

    @Test
    @DisplayName("корректно создавать книгу")
    void correctCreateBook() throws Exception {
        mvc.perform(post("/books")
                        .param("name", "We")
                        .param("author.name", "Zamiatin")
                        .param("genre.name", "Fantasy"))
                .andExpect(status().isOk());

        Mockito.verify(bookService, times(1)).create("We", "Zamiatin", "Fantasy");
    }
}