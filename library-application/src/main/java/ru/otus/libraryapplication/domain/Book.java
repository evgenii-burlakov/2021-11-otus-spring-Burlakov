package ru.otus.libraryapplication.domain;

import lombok.Data;

@Data
public class Book {
    private final Long id;
    private final String name;
    private final Author author;
    private final Genre genre;
}
