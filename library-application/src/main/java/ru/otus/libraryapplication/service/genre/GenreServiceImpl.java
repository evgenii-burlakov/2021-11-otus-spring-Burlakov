package ru.otus.libraryapplication.service.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.libraryapplication.domain.Genre;
import ru.otus.libraryapplication.dto.GenreDto;
import ru.otus.libraryapplication.repositories.genre.GenreRepository;
import ru.otus.libraryapplication.service.string.StringService;
import ru.otus.libraryapplication.util.exeption.ApplicationException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final StringService stringService;

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> getAll() {
        return genreRepository.findAll().stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public GenreDto getById(long id) {
        return genreRepository.findById(id).map(GenreDto::toDto).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getByName(String name) {
        return genreRepository.findByName(name).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        genreRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(GenreDto genreDto) {
        String genreName = stringService.beautifyStringName(genreDto.getName());
        if (stringService.verifyNotBlank(genreName)) {
            genreRepository.findById(genreDto.getId()).ifPresentOrElse(genre -> {
                genre.setName(genreName);
                genreRepository.save(genre);
            }, () -> {
                throw new ApplicationException("Invalid genre id");
            });
        } else {
            throw new ApplicationException("Invalid genre name");
        }
    }

    @Override
    @Transactional
    public GenreDto create(GenreDto genreDto) {
        String genreName = stringService.beautifyStringName(genreDto.getName());
        if (stringService.verifyNotBlank(genreName)) {
            Genre genre = new Genre(null, genreName);
            return GenreDto.toDto(genreRepository.save(genre));
        }

        throw new ApplicationException("Invalid genre name");
    }
}
