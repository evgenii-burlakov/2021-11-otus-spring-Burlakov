package ru.otus.libraryapplication.repositories.book;

public interface BookRepositoryCustom {
    boolean existByBookAuthorAndGenreNames(String bookName, String author, String genre);

    void deleteWithCommentsByBookId(String id);
}
