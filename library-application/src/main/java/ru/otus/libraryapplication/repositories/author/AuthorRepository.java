package ru.otus.libraryapplication.repositories.author;

import ru.otus.libraryapplication.domain.Author;

import java.util.List;

public interface AuthorRepository {
    List<Author> getAll();

    Author getById(long Id);

    Author getByName(String name);

    void deleteById(long id);

    Author create(Author author);
}
