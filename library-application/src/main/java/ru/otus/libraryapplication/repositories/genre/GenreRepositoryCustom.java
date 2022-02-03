package ru.otus.libraryapplication.repositories.genre;

public interface GenreRepositoryCustom {
    void deleteWithBooksByGenreId(String id);
}
