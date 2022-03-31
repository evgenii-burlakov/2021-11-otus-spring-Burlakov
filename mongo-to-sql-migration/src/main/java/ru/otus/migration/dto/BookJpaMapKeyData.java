package ru.otus.migration.dto;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import ru.otus.migration.jpaModel.BookJpa;

@EqualsAndHashCode
@RequiredArgsConstructor
public class BookJpaMapKeyData {
    private final String bookName;
    private final String authorName;
    private final String genreName;

    public static BookJpaMapKeyData fromBookJpa(BookJpa bookJpa) {
        return new BookJpaMapKeyData(bookJpa.getName(), bookJpa.getAuthor().getName(), bookJpa.getGenre().getName());
    }
}
