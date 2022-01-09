package ru.otus.libraryapplication.service.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.libraryapplication.dao.book.BookDao;
import ru.otus.libraryapplication.dao.genre.GenreDao;
import ru.otus.libraryapplication.domain.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDao dao;

    @Override
    public List<Genre> getAll() {
        return dao.getAll();
    }

    @Override
    public Genre getById(long id) {
        return dao.getById(id);
    }

    @Override
    public void deleteById(long id) {
        dao.deleteById(id);
    }

    @Override
    public void update(long id, String name) {
        dao.update(id, name);
    }

    @Override
    public void create(String name) {
        dao.create(name);
    }
}
