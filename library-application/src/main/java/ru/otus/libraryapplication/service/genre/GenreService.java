package ru.otus.libraryapplication.service.genre;

import ru.otus.libraryapplication.domain.Genre;
import ru.otus.libraryapplication.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> getAll();

    GenreDto getById(long id);

    Genre getByName(String name);

    void deleteById(long id);

    void update(GenreDto genreDto);

    GenreDto create(GenreDto genreDto);
}
