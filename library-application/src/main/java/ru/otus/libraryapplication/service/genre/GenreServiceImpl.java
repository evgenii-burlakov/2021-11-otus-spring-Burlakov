package ru.otus.libraryapplication.service.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.libraryapplication.dao.genre.GenreDao;
import ru.otus.libraryapplication.domain.Genre;
import ru.otus.libraryapplication.service.author.AuthorService;
import ru.otus.libraryapplication.service.string.StringService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDao dao;
    private final AuthorService authorService;
    private final StringService stringService;

    @Override
    public List<Genre> getAll() {
        return dao.getAll();
    }

    @Override
    public Genre getById(long id) {
        return dao.getById(id);
    }

    @Override
    public Genre getByName(String name) {
        return dao.getByName(name);
    }

    @Override
    public void deleteById(long id) {
        authorService.deleteAllUnusedAuthorByGenresId(id);

        dao.deleteById(id);
    }

    @Override
    public void update(long id, String name) {
        String genreName = stringService.beautifyStringName(name);
        dao.update(id, genreName);
    }

    @Override
    public long create(String name) {
        String genreName = stringService.beautifyStringName(name);
        return dao.create(genreName);
    }

    @Override
    public void deleteAllUnusedGenresByAuthorId(long authorId) {
        dao.getUniqueGenresToAuthor(authorId).forEach(dao::deleteById);
    }
}
