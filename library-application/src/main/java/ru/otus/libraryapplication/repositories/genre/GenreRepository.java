package ru.otus.libraryapplication.repositories.genre;

import ru.otus.libraryapplication.domain.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> getAll();

    Genre getById(long id);

    Genre getByName(String name);

    void deleteById(long id);

    void update(Genre genre);

    Genre create(Genre genre);
}
