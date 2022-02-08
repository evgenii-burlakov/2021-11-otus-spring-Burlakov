package ru.otus.libraryapplication.service.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.dto.AuthorDto;
import ru.otus.libraryapplication.repositories.author.AuthorRepository;
import ru.otus.libraryapplication.service.string.StringService;
import ru.otus.libraryapplication.util.exeption.ApplicationException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final StringService stringService;

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> getAll() {
        return authorRepository.findAll().stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDto getById(long id) {
        return authorRepository.findById(id).map(AuthorDto::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Author getByName(String author) {
        return authorRepository.findByName(author).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        authorRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(AuthorDto authorDto) {
        String authorName = stringService.beautifyStringName(authorDto.getName());
        if (stringService.verifyNotBlank(authorName)) {
            authorRepository.findById(authorDto.getId()).ifPresentOrElse(author -> {
                author.setName(authorName);
                authorRepository.save(author);
            }, () -> {
                throw new ApplicationException("Invalid author id");
            });
        } else {
            throw new ApplicationException("Invalid author name");
        }
    }

    @Override
    @Transactional
    public Author create(AuthorDto authorDto) {
        String authorName = stringService.beautifyStringName(authorDto.getName());
        if (stringService.verifyNotBlank(authorName)) {
            Author author = new Author(null, authorName);
            return authorRepository.save(author);
        }

        throw new ApplicationException("Invalid author name");
    }
}
