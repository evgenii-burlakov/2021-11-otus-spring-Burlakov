package ru.otus.libraryapplication.service.author;

import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAll();

    AuthorDto getById(long id);

    Author getByName(String author);

    void deleteById(long id);

    void update(AuthorDto authorDto);

    Author create(AuthorDto authorDto);
}
