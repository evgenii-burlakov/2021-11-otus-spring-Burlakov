package ru.otus.libraryapplication.repositories.author;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DataMongoTest
@EnableConfigurationProperties
@DisplayName("Репозиторий для работы с авторами должен ")
class AuthorRepositoryCustomImplTest {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private MongoTemplate testTemplate;

    @DisplayName("каскадно удалять книги при удалении автора")
    @Test
    void shouldCascadeDeleteBooksWithAuthor() {
        Author author = testTemplate.findOne(Query.query(Criteria.where("name").is("PUSHKIN")), Author.class);

        authorRepository.deleteWithBooksByAuthorId(author.getId());

        assertThat(testTemplate.findById(author.getId(), Author.class)).isNull();
        assertThat(testTemplate.find(Query.query(Criteria.where("author.id").is(author.getId())), Book.class)).isEqualTo(List.of());
    }


    @DisplayName("не выкидывать ошибку при некорректном значении ИД ")
    @Test
    void shouldNotThrowExceptionOnIncorrectDeletion() {
        assertThatCode(() -> authorRepository.deleteWithBooksByAuthorId(null)).doesNotThrowAnyException();
    }
}