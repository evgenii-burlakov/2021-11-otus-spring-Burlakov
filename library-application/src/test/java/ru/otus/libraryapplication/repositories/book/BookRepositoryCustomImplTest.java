package ru.otus.libraryapplication.repositories.book;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.domain.Comment;
import ru.otus.libraryapplication.domain.Genre;
import ru.otus.libraryapplication.repositories.comment.CommentRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DataMongoTest
@EnableConfigurationProperties
@DisplayName("Репозиторий для работы с книгами должен ")
class BookRepositoryCustomImplTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MongoTemplate testTemplate;

    @DisplayName("возвращать true, если такая книга уже есть в БД")
    @Test
    void shouldReturnTrueIfEqualBookExist() {
        Author author = testTemplate.findOne(Query.query(Criteria.where("name").is("PUSHKIN")), Author.class);
        Genre genre = testTemplate.findOne(Query.query(Criteria.where("name").is("POEM")), Genre.class);

        boolean actual = bookRepository.existByBookAuthorAndGenre("EVGENII ONEGIN", author, genre);
        assertThat(actual).isTrue();
    }

    @DisplayName("возвращать false, если такой книги нет в БД")
    @Test
    void shouldReturnFalseIfEqualBookNotExist() {
        Author author = testTemplate.findOne(Query.query(Criteria.where("name").is("LERMONTOV")), Author.class);
        Genre genre = testTemplate.findOne(Query.query(Criteria.where("name").is("POEM")), Genre.class);

        boolean actual = bookRepository.existByBookAuthorAndGenre("EVGENII ONEGIN", author, genre);
        assertThat(actual).isFalse();
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("каскадно удалять комментарии при удалении книги")
    @Test
    void shouldCascadeDeleteCommentsWithBook() {
        Book book = testTemplate.findOne(Query.query(Criteria.where("name").is("EVGENII ONEGIN")), Book.class);

        bookRepository.deleteWithCommentsByBookId(book.getId());

        assertThat(bookRepository.findById(book.getId())).isEqualTo(Optional.empty());
        assertThat(commentRepository.getAllByBook(book)).isEqualTo(List.of());

        assertThat(testTemplate.findById(book.getId(), Book.class)).isNull();
        assertThat(testTemplate.find(Query.query(Criteria.where("book.id").is(book.getId())), Comment.class)).isEqualTo(List.of());
    }


    @DisplayName("не выкидывать ошибку при некорректном значении ИД ")
    @Test
    void shouldNotThrowExceptionOnIncorrectDeletion() {
        assertThatCode(() -> bookRepository.deleteWithCommentsByBookId(null)).doesNotThrowAnyException();
    }
}