package ru.otus.libraryapplication.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.domain.Comment;
import ru.otus.libraryapplication.domain.Genre;
import ru.otus.libraryapplication.repositories.author.AuthorRepository;
import ru.otus.libraryapplication.repositories.book.BookRepository;
import ru.otus.libraryapplication.repositories.comment.CommentRepository;
import ru.otus.libraryapplication.repositories.genre.GenreRepository;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Author author1;
    private Author author2;
    private Genre genre1;
    private Genre genre2;
    private Book book;

    @ChangeSet(order = "000", id = "dropDB", author = "esburlakov", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initGenres", author = "esburlakov", runAlways = true)
    public void initGenres(GenreRepository repository){
        genre1 = repository.save(new Genre(null, "POEM"));
        genre2 = repository.save(new Genre(null, "NOVEL"));
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "esburlakov", runAlways = true)
    public void initAuthors(AuthorRepository repository){
        author1 = repository.save(new Author(null, "PUSHKIN"));
        author2 = repository.save(new Author(null, "MONTGOMERY"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "esburlakov", runAlways = true)
    public void initBooks(BookRepository repository){
        book = repository.save(new Book(null, "EVGENII ONEGIN", author1, genre1));
        repository.save(new Book(null, "ANNE OF GREEN GABLES", author2, genre2));
    }

    @ChangeSet(order = "004", id = "initComments", author = "esburlakov", runAlways = true)
    public void initComments(CommentRepository repository){
        repository.save(new Comment(null, "Хорошая книга", book));
        repository.save(new Comment(null, "Огонь", book));
    }
}
