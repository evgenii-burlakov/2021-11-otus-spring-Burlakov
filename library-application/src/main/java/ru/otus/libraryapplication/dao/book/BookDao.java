package ru.otus.libraryapplication.dao.book;

import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.domain.Genre;

import java.util.List;

public interface BookDao {
    List<Book> getAll();

    Book getById(long id);

    void deleteById(long id);

    long create(String bookName, Author bookAuthor, Genre bookGenre);

    void update(Book book);

    boolean isEqualBookExist(String bookName, String author, String genre);
}
