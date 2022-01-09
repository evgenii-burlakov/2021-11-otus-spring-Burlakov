package ru.otus.libraryapplication.service.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.libraryapplication.dao.author.AuthorDao;
import ru.otus.libraryapplication.dao.book.BookDao;
import ru.otus.libraryapplication.domain.Author;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao dao;

    @Override
    public List<Author> getAll() {
        return dao.getAll();
    }

    @Override
    public Author getById(long id) {
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
