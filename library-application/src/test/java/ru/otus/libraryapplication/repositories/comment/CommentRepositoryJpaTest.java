package ru.otus.libraryapplication.repositories.comment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.libraryapplication.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static ru.otus.libraryapplication.LibraryUnitTestData.*;

@DataJpaTest
@DisplayName("Репозиторий для работы с комментариями должен ")
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {

    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("возвращать все комментарии из БД по книге")
    @Test
    void shouldReturnAllCommentsByBook() {
        List<Comment> expectedComments = List.of(COMMENT1, COMMENT2, COMMENT3);
        List<Comment> actualComments = commentRepositoryJpa.getAllByBook(BOOK1);
        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @DisplayName("возвращать пустой список комментариев из БД по книге, если их там не существует")
    @Test
    void shouldReturnEmptyCommentsListByBook() {
        List<Comment> expectedComments = List.of();
        List<Comment> actualComments = commentRepositoryJpa.getAllByBook(BOOK2);
        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @DisplayName("удалять комментарий из БД, если он там существует")
    @Test
    void shouldCorrectDeleteCommentById() {
        commentRepositoryJpa.deleteById(1);
        assertThat(em.find(Comment.class, 1L)).isNull();
    }

    @DisplayName("не генерировать ошибки при удалении по ИД комментария из БД, если его там не существует")
    @Test
    void shouldDoesNotThrowExceptionsWhereDeleteNonExistCommentById() {
        assertThatCode(() -> commentRepositoryJpa.deleteById(4)).doesNotThrowAnyException();
    }

    @DisplayName("записывать новый комментарий в БД")
    @Test
    void createNewComment() {
        Comment actualComment = commentRepositoryJpa.create(new Comment(null, "TEST", BOOK1));
        Comment expectedComment = new Comment(4L, "TEST", BOOK1);
        assertThat(actualComment).isEqualTo(expectedComment);
    }

    @DisplayName("обновлять комментарий в БД, если он там существует")
    @Test
    void updateExistingComment() {
        Comment newComment = new Comment(1L, "Читал его в ПТУ", BOOK1);
        commentRepositoryJpa.update(newComment);
        Comment actualComment = em.find(Comment.class, 1L);
        Comment expectedComment = new Comment(1L, "Читал его в ПТУ", BOOK1);
        assertThat(actualComment).isEqualTo(expectedComment);
    }

    @DisplayName("не генерировать ошибки при обновлении комментария в БД, если его там не существует")
    @Test
    void updateNonExistComment() {
        Comment newComment = new Comment(5L, "Читал его в ПТУ", BOOK1);
        assertThatCode(() -> commentRepositoryJpa.update(newComment)).doesNotThrowAnyException();
    }
}