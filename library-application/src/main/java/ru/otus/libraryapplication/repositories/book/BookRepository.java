package ru.otus.libraryapplication.repositories.book;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.libraryapplication.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, Long>, BookRepositoryCustom {
    List<Book> findAll();

    Optional<Book> findById(String id);

    Book save(Book book);
}
