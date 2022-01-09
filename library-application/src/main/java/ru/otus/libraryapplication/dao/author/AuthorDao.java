package ru.otus.libraryapplication.dao.author;

import ru.otus.libraryapplication.domain.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> getAll();

    Author getById(long Id);

    void deleteById(long id);

    void update(long id, String name);

    void create(String name);
}
