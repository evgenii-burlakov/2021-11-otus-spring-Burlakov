package ru.otus.libraryapplication.service.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.libraryapplication.dao.author.AuthorDao;
import ru.otus.libraryapplication.dao.genre.GenreDao;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.service.string.StringService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final StringService stringService;

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

    @Override
    public Author getById(long id) {
        return authorDao.getById(id);
    }

    @Override
    public Author getByName(String author) {
        return authorDao.getByName(author);
    }

    @Override
    public void deleteById(long id) {
        genreDao.getUniqueGenresToAuthor(id).forEach(genreDao::deleteById);

        authorDao.deleteById(id);
    }

    @Override
    public void update(long id, String name) {
        String authorName = stringService.beautifyStringName(name);
        if (stringService.verifyNotBlank(authorName)) {
            authorDao.update(id, authorName);
        }
    }

    @Override
    public Long create(String name) {
        String authorName = stringService.beautifyStringName(name);
        if (stringService.verifyNotBlank(authorName)) {
            return authorDao.create(authorName);
        }

        return null;
    }
}
