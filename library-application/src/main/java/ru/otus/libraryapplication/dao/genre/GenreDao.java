package ru.otus.libraryapplication.dao.genre;

import ru.otus.libraryapplication.domain.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getAll();

    Genre getById(long id);

    Genre getByName(String name);

    void deleteById(long id);

    void update(long id, String name);

    long create(String name);

    List<Long> getUniqueGenresToAuthor(long authorId);
}
