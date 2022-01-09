package ru.otus.libraryapplication.domain;

import lombok.Data;

@Data
public class Book {
    private final long id;
    private final String name;
    private final Author author;
    private final Genre genre;
}
