package ru.otus.libraryapplication.controller.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.domain.Comment;
import ru.otus.libraryapplication.domain.Genre;
import ru.otus.libraryapplication.service.author.AuthorService;
import ru.otus.libraryapplication.service.book.BookService;
import ru.otus.libraryapplication.service.comment.CommentService;
import ru.otus.libraryapplication.service.genre.GenreService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    //Books
    @ShellMethod(value = "Get all books command", key = {"ab", "allBooks"})
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @ShellMethod(value = "Get book by id command", key = {"gb", "getBookById"})
    public Book getBookById(@ShellOption long id) {
        return bookService.getById(id);
    }

    @ShellMethod(value = "Delete book by id command", key = {"db", "deleteBookById"})
    public void deleteBookById(@ShellOption long id) {
        bookService.deleteById(id);
    }

    @ShellMethod(value = "Create book command", key = {"cb", "createBook"})
    public void createBook(@ShellOption String bookName, String authorName, String genreName) {
        bookService.create(bookName, authorName, genreName);
    }

    @ShellMethod(value = "Update book command", key = {"ub", "updateBook"})
    public void updateBook(@ShellOption long id, String bookName, String authorName, String genreName) {
        bookService.update(id, bookName, authorName, genreName);
    }


    //Authors
    @ShellMethod(value = "Get all authors command", key = {"aa", "allAuthors"})
    public List<Author> getAllAuthors() {
        return authorService.getAll();
    }

    @ShellMethod(value = "Get author by id command", key = {"ga", "getAuthorById"})
    public Author getAuthorById(@ShellOption long id) {
        return authorService.getById(id);
    }

    @ShellMethod(value = "Delete author by id command", key = {"da", "deleteAuthorById"})
    public void deleteAuthorById(@ShellOption long id) {
        authorService.deleteById(id);
    }

    @ShellMethod(value = "Update author by id command", key = {"ua", "updateAuthor"})
    public void updateAuthor(@ShellOption long id, String name) {
        authorService.update(id, name);
    }

    @ShellMethod(value = "Create author command", key = {"ca", "createAuthor"})
    public void createAuthor(@ShellOption String name) {
        authorService.create(name);
    }


    //Genres
    @ShellMethod(value = "Get all genres command", key = {"ag", "allGenres"})
    public List<Genre> getAllGenres() {
        return genreService.getAll();
    }

    @ShellMethod(value = "Get genre by id command", key = {"gg", "getGenreById"})
    public Genre getGenreById(@ShellOption long id) {
        return genreService.getById(id);
    }

    @ShellMethod(value = "Delete genre by id command", key = {"dg", "deleteGenreById"})
    public void deleteGenreById(@ShellOption long id) {
        genreService.deleteById(id);
    }

    @ShellMethod(value = "Update genre by id command", key = {"ug", "updateGenre"})
    public void updateGenre(@ShellOption long id, String name) {
        genreService.update(id, name);
    }

    @ShellMethod(value = "Create genre command", key = {"cg", "createGenre"})
    public void createGenre(@ShellOption String name) {
        genreService.create(name);
    }


    //Comments
    @ShellMethod(value = "Get all comments by book id", key = {"ac", "getAllCommentsByBookId"})
    public List<Comment> getAllCommentsByBookId(@ShellOption long id) {
        return commentService.getAllByBookId(id);
    }

    @ShellMethod(value = "Delete comment by id command", key = {"dc", "deleteCommentById"})
    public void deleteCommentById(@ShellOption long id) {
        commentService.deleteById(id);
    }

    @ShellMethod(value = "Update comment by id command", key = {"uc", "updateComment"})
    public void updateComment(@ShellOption long id, String name, long bookId) {
        commentService.update(id, name, bookId);
    }

    @ShellMethod(value = "Create comment command", key = {"cc", "createComment"})
    public void createComment(@ShellOption long bookId, String comment) {
        commentService.create(comment, bookId);
    }
}