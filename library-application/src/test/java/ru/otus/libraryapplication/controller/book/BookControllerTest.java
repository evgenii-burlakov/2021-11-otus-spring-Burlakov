package ru.otus.libraryapplication.controller.book;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.libraryapplication.controller.author.AuthorController;
import ru.otus.libraryapplication.dto.AuthorDto;
import ru.otus.libraryapplication.dto.BookDto;
import ru.otus.libraryapplication.dto.CommentDto;
import ru.otus.libraryapplication.service.author.AuthorService;
import ru.otus.libraryapplication.service.book.BookService;
import ru.otus.libraryapplication.service.comment.CommentService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static ru.otus.libraryapplication.LibraryUnitTestData.*;

@WebMvcTest(BookController.class)
@DisplayName("Контроллер для работы с книгами должен ")
class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;
    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("корректно возвращать все книги")
    void correctGetAllBooks() throws Exception {
        given(bookService.getAll()).willReturn(List.of(BOOK1, BOOK2, BOOK3));

        List<BookDto> expectedResult = Stream.of(BOOK1, BOOK2, BOOK3)
                .map(BookDto::toDto).collect(Collectors.toList());

        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", expectedResult));
    }

    @Test
    @DisplayName("корректно удалять книгу")
    void correctDeleteBookById() throws Exception {
        mvc.perform(post("/books/delete")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
        Mockito.verify(bookService, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("корректно возвращать страницу редактирования книги")
    void correctReturnEditPage() throws Exception {
        given(bookService.getById(1L)).willReturn(BOOK1);
        BookDto expectedResult = BookDto.toDto(BOOK1);

        mvc.perform(get("/books/edit?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editBook"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attribute("book", expectedResult));
    }

    @Test
    @DisplayName("корректно редактировать книгу")
    void correctUpdateBook() throws Exception {
        mvc.perform(post("/books/edit")
                        .param("id", "1")
                        .param("name", "We")
                        .param("author.name", "Zamiatin")
                        .param("genre.name", "Fantasy"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
        Mockito.verify(bookService, times(1)).update(1, "We", "Zamiatin", "Fantasy");
    }

    @Test
    @DisplayName("корректно возвращать страницу книги")
    void correctGetBookPage() throws Exception {
        given(bookService.getById(1L)).willReturn(BOOK1);
        given(commentService.getAllByBookId(1L)).willReturn(List.of(COMMENT1, COMMENT2, COMMENT3));

        List<CommentDto> expectedResult = Stream.of(COMMENT1, COMMENT2, COMMENT3)
                .map(CommentDto::toDto).collect(Collectors.toList());

        mvc.perform(get("/books/get/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookPage"))
                .andExpect(model().attributeExists("book", "comments"))
                .andExpect(model().attribute("book", BookDto.toDto(BOOK1)))
                .andExpect(model().attribute("comments", expectedResult));
    }

    @Test
    @DisplayName("корректно возвращать страницу создания книги")
    void correctReturnCreatePage() throws Exception {
        mvc.perform(get("/books/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createBook"));
    }

    @Test
    @DisplayName("корректно создавать книгу")
    void correctCreateBook() throws Exception {
        mvc.perform(post("/books/create")
                        .param("name", "We")
                        .param("author.name", "Zamiatin")
                        .param("genre.name", "Fantasy"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
        Mockito.verify(bookService, times(1)).create("We", "Zamiatin", "Fantasy");
    }
}