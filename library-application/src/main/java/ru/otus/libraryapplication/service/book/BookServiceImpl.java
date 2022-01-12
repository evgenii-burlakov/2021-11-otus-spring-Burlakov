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

import javax.validation.constraints.Null;
import java.util.List;
import java.util.Optional;

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
        Book book = dao.getById(id);

        dao.deleteById(id);

        long authorId = book.getAuthor().getId();
        if (dao.countByAuthor(authorId) == 0) {
            authorService.deleteById(authorId);
        }

        long genreId = book.getGenre().getId();
        if (dao.countByGenre(genreId) == 0) {
            genreService.deleteById(genreId);
        }
    }

    @Override
    public void update(long id, String name, String authorName, String genreName) {
        String author = stringService.beautifyStringName(authorName);
        String genre = stringService.beautifyStringName(genreName);
        String bookName = stringService.beautifyStringName(name);

        if (stringService.verifyNotBlank(bookName, author, genre)) {
            Book book = dao.getById(id);

            if (!book.getName().equals(bookName) || !book.getAuthor().getName().equals(authorName) || !book.getGenre().getName().equals(genreName)) {
                Author bookAuthor = book.getAuthor();
                Long oldBookAuthorForDeletion = null;
                if (!bookAuthor.getName().equals(author)) {
                    int count = dao.countByAuthor(bookAuthor.getId());
                    if (count == 1) {
                        oldBookAuthorForDeletion = bookAuthor.getId();
                    }

                    bookAuthor = getOrCreateAuthor(authorName);
                }

                Genre bookGenre = book.getGenre();
                Long oldBookGenreForDeletion = null;
                if (!bookGenre.getName().equals(genre)) {
                    int count = dao.countByGenre(bookGenre.getId());
                    if (count == 1) {
                        oldBookGenreForDeletion = bookGenre.getId();
                    }

                    bookGenre = getOrCreateGenre(genreName);
                }


                dao.update(id, bookName, bookAuthor, bookGenre);

                Optional.ofNullable(oldBookAuthorForDeletion).ifPresent(authorService::deleteById);
                Optional.ofNullable(oldBookGenreForDeletion).ifPresent(genreService::deleteById);
            }
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
            long genreId = genreService.create(genre);
            bookGenre = new Genre(genreId, genre);
        }
        return bookGenre;
    }

    private Author getOrCreateAuthor(String author) {
        Author bookAuthor = authorService.getByName(author);
        if (bookAuthor == null) {
            long authorId = authorService.create(author);
            bookAuthor = new Author(authorId, author);
        }
        return bookAuthor;
    }
}
