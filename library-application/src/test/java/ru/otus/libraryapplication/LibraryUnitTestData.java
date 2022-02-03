package ru.otus.libraryapplication;

import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.domain.Comment;
import ru.otus.libraryapplication.domain.Genre;

public final class LibraryUnitTestData {
    public static final Author AUTHOR1 = new Author("1", "PUSHKIN");
    public static final Author AUTHOR2 = new Author("2", "MONTGOMERY");

    public static final Genre GENRE1 = new Genre("3", "POEM");
    public static final Genre GENRE2 = new Genre("4", "NOVEL");

    public static final Book BOOK1 = new Book("5", "EVGENII ONEGIN", AUTHOR1, GENRE1);
    public static final Book BOOK2 = new Book("6", "ANNE OF GREEN GABLES", AUTHOR2, GENRE2);
    public static final Book BOOK3 = new Book("7", "ANNE OF GREEN GABLES POEM EDITION", AUTHOR2, GENRE1);

    public static final Comment COMMENT1 = new Comment("8", "ЧИТАЛ ЕЕ В ШКОЛЕ", BOOK1);
    public static final Comment COMMENT2 = new Comment("9", "Пушкин ван лав", BOOK1);
    public static final Comment COMMENT3 = new Comment("10", "СкУчНоТиЩа", BOOK1);
}
