package ru.otus.libraryapplication.repositories.genre;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.libraryapplication.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, Long>, GenreRepositoryCustom {
    List<Genre> findAll();

    Optional<Genre> findById(String id);

    Optional<Genre> findByName(String name);

    Genre save(Genre genre);
}
