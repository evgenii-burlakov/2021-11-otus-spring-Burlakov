package ru.otus.libraryapplication.dao.author;

import ru.otus.libraryapplication.domain.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> getAll();

    Author getById(long Id);

    Author getByName(String name);

    void deleteById(long id);

    void update(long id, String name);

    long create(String name);

    List<Long> getUniqueAuthorsToGenre(long genreId);
}
