package ru.otus.libraryapplication.service.author;

import ru.otus.libraryapplication.domain.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();

    Author getById(long id);

    void deleteById(long id);

    void update(long id, String name);

    void create(String name);
}
