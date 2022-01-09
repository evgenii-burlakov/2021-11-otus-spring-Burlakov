package ru.otus.libraryapplication.service.genre;

import ru.otus.libraryapplication.domain.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAll();

    Genre getById(long id);

    void deleteById(long id);

    void update(long id, String name);

    void create(String name);
}
