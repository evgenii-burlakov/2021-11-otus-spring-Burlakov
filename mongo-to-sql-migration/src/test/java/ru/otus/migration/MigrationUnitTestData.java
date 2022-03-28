package ru.otus.migration;

import ru.otus.migration.jpaModel.AuthorJpa;
import ru.otus.migration.jpaModel.BookJpa;
import ru.otus.migration.jpaModel.CommentJpa;
import ru.otus.migration.jpaModel.GenreJpa;

public class MigrationUnitTestData {
    public static final AuthorJpa AUTHOR1 = new AuthorJpa(10L, "PUSHKIN");
    public static final AuthorJpa AUTHOR2 = new AuthorJpa(2L, "MONTGOMERY");

    public static final GenreJpa GENRE1 = new GenreJpa(1L, "POEM");
    public static final GenreJpa GENRE2 = new GenreJpa(7L, "NOVEL");

    public static final BookJpa BOOK1 = new BookJpa(10L, "EVGENII ONEGIN", AUTHOR1, GENRE1);
    public static final BookJpa BOOK2 = new BookJpa(2L, "ANNE OF GREEN GABLES", AUTHOR2, GENRE2);

    public static final CommentJpa COMMENT1 = new CommentJpa(1L, "Хорошая книга", BOOK1);
    public static final CommentJpa COMMENT2 = new CommentJpa(2L, "Огонь", BOOK1);
}
