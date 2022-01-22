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
    public List<Comment> getAllByBookId(Long bookId) {
        TypedQuery<Book> query = em.createQuery("select b " +
                        "from Book b " +
                        "join fetch b.author " +
                        "join fetch b.genre",
                Book.class);

        return query.getResultList();
    }

    @Override
    public Comment getById(long id) {
        return em.find(Book.class, id);
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
                "set c.comment = :comment " +
                "where c.id = :id");
        query.setParameter("name", comment.getComment());
        query.setParameter("id", comment.getId());
        query.executeUpdate();
    }
}
