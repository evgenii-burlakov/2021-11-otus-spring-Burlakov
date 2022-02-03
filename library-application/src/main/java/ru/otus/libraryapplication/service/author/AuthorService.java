package ru.otus.libraryapplication.service.author;

import ru.otus.libraryapplication.domain.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAll();

    Author getById(String id);

    Author getByName(String author);

    void deleteById(String id);

    void update(String id, String name);

    Author create(String name);
}
