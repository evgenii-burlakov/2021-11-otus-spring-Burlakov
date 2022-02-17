package ru.otus.libraryapplication.controller.comment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.libraryapplication.controller.book.BookController;
import ru.otus.libraryapplication.dto.BookDto;
import ru.otus.libraryapplication.dto.CommentDto;
import ru.otus.libraryapplication.service.book.BookService;
import ru.otus.libraryapplication.service.comment.CommentService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.otus.libraryapplication.LibraryUnitTestData.*;

@WebMvcTest(CommentController.class)
@DisplayName("Контроллер для работы с комментариями должен ")
class CommentControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("корректно возвращать комментарий по ИД")
    void correctReturnCommentById() throws Exception {
        given(commentService.getById(1L)).willReturn(COMMENT1);

        mvc.perform(get("/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("comment", is("ЧИТАЛ ЕЕ В ШКОЛЕ")))
                .andExpect(jsonPath("book.id", is(1)));
    }

    @Test
    @DisplayName("корректно возвращать комментарий по ИД книги")
    void correctReturnCommentByBookId() throws Exception {
        given(commentService.getAllByBookId(1L)).willReturn(List.of(COMMENT1,COMMENT2));

        mvc.perform(get("/comments?bookId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].comment", is("ЧИТАЛ ЕЕ В ШКОЛЕ")))
                .andExpect(jsonPath("$[0].book.id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].comment", is("Пушкин ван лав")))
                .andExpect(jsonPath("$[1].book.id", is(1)));
    }

    @Test
    @DisplayName("корректно создавать комментарий")
    void correctCreateComment() throws Exception {
        mvc.perform(post("/comments")
                        .param("comment", "Nice")
                        .param("book.id", "1"))
                .andExpect(status().isOk());

        Mockito.verify(commentService, times(1)).create("Nice", 1L);
    }


    @Test
    @DisplayName("корректно редактировать комментарий")
    void correctUpdateComment() throws Exception {
        mvc.perform(patch("/comments/1")
                        .param("id", "1")
                        .param("comment", "Nice")
                        .param("book.id", "1"))
                .andExpect(status().isOk());

        Mockito.verify(commentService, times(1)).update(1, "Nice", 1L);
    }

    @Test
    @DisplayName("корректно удалять комментарий")
    void correctDeleteBookById() throws Exception {
        mvc.perform(delete("/comments")
                        .param("id", "1"))
                .andExpect(status().isOk());

        Mockito.verify(commentService, times(1)).deleteById(1L);
    }
}