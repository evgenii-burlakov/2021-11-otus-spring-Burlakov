package ru.otus.libraryapplication.service.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.repositories.author.AuthorRepository;
import ru.otus.libraryapplication.service.string.StringService;
import ru.otus.libraryapplication.util.exeption.ApplicationException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final StringService stringService;

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAll() {
        return authorRepository.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Author getById(long id) {
        return authorRepository.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Author getByName(String author) {
        return authorRepository.getByName(author);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        authorRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(long id, String name) {
        String authorName = stringService.beautifyStringName(name);
        if (stringService.verifyNotBlank(authorName)) {
            if (authorRepository.getById(id) != null) {
                Author author = new Author(id, authorName);
                authorRepository.create(author);
            } else {
                throw new ApplicationException("Invalid author id");
            }
        } else {
            throw new ApplicationException("Invalid author name");
        }
    }

    @Override
    @Transactional
    public Author create(String name) {
        String authorName = stringService.beautifyStringName(name);
        if (stringService.verifyNotBlank(authorName)) {
            Author author = new Author(null, authorName);
            return authorRepository.create(author);
        }

        throw new ApplicationException("Invalid author name");
    }
}
