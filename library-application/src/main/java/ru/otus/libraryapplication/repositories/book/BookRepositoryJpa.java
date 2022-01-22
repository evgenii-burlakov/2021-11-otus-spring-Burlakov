package ru.otus.libraryapplication.repositories.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.libraryapplication.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select b " +
                        "from Book b " +
                        "join fetch b.author " +
                        "join fetch b.genre",
                Book.class);

        return query.getResultList();
    }

    @Override
    public Book getById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public boolean existByBookAuthorAndGenreNames(String bookName, String author, String genre) {
        TypedQuery<Long> query = em.createQuery("select count(*) " +
                "from Book b " +
                "inner join Author a on b.author=a.id " +
                "inner join Genre g on b.genre=g.id " +
                "where b.name = :bookName " +
                "and a.name = :author " +
                "and g.name = :genre", Long.class);

        query.setParameter("bookName", bookName);
        query.setParameter("author", author);
        query.setParameter("genre", genre);

        return query.getSingleResult() != 0;
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Book b " +
                "where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Book create(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public void update(Book book) {
        Query query = em.createQuery("update Book b " +
                "set b.name = :name, " +
                "b.author = :author, " +
                "b.genre = :genre " +
                "where b.id = :id");
        query.setParameter("id", book.getId());
        query.setParameter("name", book.getName());
        query.setParameter("author", book.getAuthor());
        query.setParameter("genre", book.getGenre());
        query.executeUpdate();
    }
}
