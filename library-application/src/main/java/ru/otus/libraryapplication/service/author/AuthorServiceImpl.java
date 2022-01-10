package ru.otus.libraryapplication.service.author;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.otus.libraryapplication.dao.author.AuthorDao;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.domain.Genre;
import ru.otus.libraryapplication.service.genre.GenreService;
import ru.otus.libraryapplication.service.string.StringService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao dao;
    private final GenreService genreService;
    private final StringService stringService;

    @Override
    public List<Author> getAll() {
        return dao.getAll();
    }

    @Override
    public Author getById(long id) {
        return dao.getById(id);
    }

    @Override
    public Author getByName(String author) {
        return dao.getByName(author);
    }

    @Override
    public void deleteById(long id) {
        genreService.deleteAllUnusedGenresByAuthorId(id);

        dao.deleteById(id);
    }

    @Override
    public void update(long id, String name) {
        String authorName = stringService.beautifyStringName(name);
        dao.update(id, authorName);
    }

    @Override
    public long create(String name) {
        String authorName = stringService.beautifyStringName(name);
        return dao.create(authorName);
    }

    @Override
    public void deleteAllUnusedAuthorByGenresId(long genreId) {
        dao.getUniqueAuthorsToGenre(genreId).forEach(dao::deleteById);
    }
}
