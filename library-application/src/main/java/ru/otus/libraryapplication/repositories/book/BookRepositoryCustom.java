package ru.otus.libraryapplication.repositories.book;

import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.domain.Genre;

public interface BookRepositoryCustom {
    boolean existByBookAuthorAndGenre(String bookName, Author author, Genre genre);

    void deleteWithCommentsByBookId(String id);
}
