package ru.otus.libraryapplication.repositories.book;

import reactor.core.publisher.Mono;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.domain.Genre;

public interface BookRepositoryCustom {
    Mono<Boolean> existByBookAuthorAndGenre(String bookName, Author author, Genre genre);

    void deleteWithCommentsByBookId(String id);
}
