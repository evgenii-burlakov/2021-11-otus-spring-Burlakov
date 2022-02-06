package ru.otus.libraryapplication.repositories.genre;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DataMongoTest
@EnableConfigurationProperties
@DisplayName("Репозиторий для работы с жанрами должен ")
class GenreRepositoryCustomImplTest {
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private MongoTemplate testTemplate;

    @DisplayName("каскадно удалять книги при удалении жанра")
    @Test
    void shouldCascadeDeleteBooksWithGenre() {
        Genre genre = testTemplate.findOne(Query.query(Criteria.where("name").is("POEM")), Genre.class);

        genreRepository.deleteWithBooksByGenreId(genre.getId());

        assertThat(testTemplate.findById(genre.getId(), Genre.class)).isNull();
        assertThat(testTemplate.find(Query.query(Criteria.where("genre.id").is(genre.getId())), Book.class)).isEqualTo(List.of());
    }


    @DisplayName("не выкидывать ошибку при некорректном значении ИД ")
    @Test
    void shouldNotThrowExceptionOnIncorrectDeletion() {
        assertThatCode(() -> genreRepository.deleteWithBooksByGenreId(null)).doesNotThrowAnyException();
    }
}