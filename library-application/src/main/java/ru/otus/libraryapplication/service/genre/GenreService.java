package ru.otus.libraryapplication.service.genre;

import ru.otus.libraryapplication.domain.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAll();

    Genre getById(String id);

    Genre getByName(String name);

    void deleteById(String id);

    void update(String id, String name);

    Genre create(String name);
}
