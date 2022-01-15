package ru.otus.libraryapplication.service.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.libraryapplication.dao.book.BookDao;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.domain.Genre;
import ru.otus.libraryapplication.service.author.AuthorService;
import ru.otus.libraryapplication.service.genre.GenreService;
import ru.otus.libraryapplication.service.string.StringService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao dao;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final StringService stringService;

    @Override
    public List<Book> getAll() {
        return dao.getAll();
    }

    @Override
    public Book getById(long id) {
        return dao.getById(id);
    }

    @Override
    public void deleteById(long id) {
        dao.deleteById(id);
    }

    @Override
    public void update(long id, String name, String authorName, String genreName) {
        String author = stringService.beautifyStringName(authorName);
        String genre = stringService.beautifyStringName(genreName);
        String bookName = stringService.beautifyStringName(name);

        if (stringService.verifyNotBlank(bookName, author, genre)) {
            Author bookAuthor = getOrCreateAuthor(author);
            Genre bookGenre = getOrCreateGenre(genre);
            Book book = new Book(id, bookName, bookAuthor, bookGenre);
            dao.update(book);
        }
    }

    @Override
    public void create(String name, String authorName, String genreName) {
        String author = stringService.beautifyStringName(authorName);
        String genre = stringService.beautifyStringName(genreName);
        String bookName = stringService.beautifyStringName(name);

        if (stringService.verifyNotBlank(bookName, author, genre) && !dao.isEqualBookExist(bookName, author, genre)) {
            Author bookAuthor = getOrCreateAuthor(author);

            Genre bookGenre = getOrCreateGenre(genre);

            dao.create(bookName, bookAuthor, bookGenre);
        }
    }

    private Genre getOrCreateGenre(String genre) {
        Genre bookGenre = genreService.getByName(genre);
        if (bookGenre == null) {
            Long genreId = genreService.create(genre);
            bookGenre = genreId == null ? null : new Genre(genreId, genre);
        }
        return bookGenre;
    }

    private Author getOrCreateAuthor(String author) {
        Author bookAuthor = authorService.getByName(author);
        if (bookAuthor == null) {
            Long authorId = authorService.create(author);
            bookAuthor = authorId == null ? null : new Author(authorId, author);
        }
        return bookAuthor;
    }
}
