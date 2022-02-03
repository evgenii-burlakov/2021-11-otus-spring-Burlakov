package ru.otus.libraryapplication.repositories.author;

public interface AuthorRepositoryCustom {
    void deleteWithBooksByAuthorId(String id);
}
