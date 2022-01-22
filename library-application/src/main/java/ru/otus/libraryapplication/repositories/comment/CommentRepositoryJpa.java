package ru.otus.libraryapplication.repositories.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Comment> getAllByBook(Book book) {
        TypedQuery<Comment> query = em.createQuery("select c " +
                        "from Comment c " +
                        "where c.book= :book ",
                Comment.class);
        query.setParameter("book", book);

        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Comment c " +
                "where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Comment create(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public void update(Comment comment) {
        Query query = em.createQuery("update Comment c " +
                "set c.comment = :comment, " +
                "c.book = :book " +
                "where c.id = :id");
        query.setParameter("comment", comment.getComment());
        query.setParameter("book", comment.getBook());
        query.setParameter("id", comment.getId());
        query.executeUpdate();
    }
}
