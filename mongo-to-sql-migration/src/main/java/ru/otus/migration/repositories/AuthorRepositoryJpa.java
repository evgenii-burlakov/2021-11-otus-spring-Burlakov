package ru.otus.migration.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.migration.jpaModel.AuthorJpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJpa {

    @PersistenceContext
    private final EntityManager em;

    public AuthorJpa getByName(String name) {
        TypedQuery<AuthorJpa> query = em.createQuery("select a " +
                        "from AuthorJpa a " +
                        "where a.name = :name",
                AuthorJpa.class);
        query.setParameter("name", name);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
