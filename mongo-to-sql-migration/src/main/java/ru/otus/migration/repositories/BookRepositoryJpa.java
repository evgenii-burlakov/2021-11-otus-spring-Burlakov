package ru.otus.migration.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.migration.jpaModel.BookJpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa {

    @PersistenceContext
    private final EntityManager em;

    public BookJpa getByBookAuthorAndGenreNames(String bookName, String author, String genre) {
        TypedQuery<BookJpa> query = em.createQuery("select b " +
                "from BookJpa b " +
                "inner join AuthorJpa a on b.author=a.id " +
                "inner join GenreJpa g on b.genre=g.id " +
                "where b.name = :bookName " +
                "and a.name = :author " +
                "and g.name = :genre", BookJpa.class);

        query.setParameter("bookName", bookName);
        query.setParameter("author", author);
        query.setParameter("genre", genre);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
