package ru.otus.libraryapplication.repositories.author;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.libraryapplication.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, Long>, AuthorRepositoryCustom {
    List<Author> findAll();

    Optional<Author> findById(String Id);

    Optional<Author> findByName(String name);

    Author save(Author author);
}
