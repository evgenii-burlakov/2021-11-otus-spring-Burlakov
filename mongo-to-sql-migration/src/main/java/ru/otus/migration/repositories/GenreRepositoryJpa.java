package ru.otus.migration.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.migration.jpaModel.GenreJpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryJpa {

    @PersistenceContext
    private final EntityManager em;

    public GenreJpa getByName(String name) {
        TypedQuery<GenreJpa> query = em.createQuery("select a " +
                        "from GenreJpa a " +
                        "where a.name = :name",
                GenreJpa.class);
        query.setParameter("name", name);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
