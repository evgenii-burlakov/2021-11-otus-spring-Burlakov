package ru.otus.libraryapplication.controller.genre;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ru.otus.libraryapplication.domain.Genre;
import ru.otus.libraryapplication.dto.GenreDto;
import ru.otus.libraryapplication.repositories.genre.GenreRepository;
import ru.otus.libraryapplication.service.genre.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GenreController {
//    private final GenreService genreService;
    private final GenreRepository genreRepository;

    @GetMapping("/api/genres")
    public Flux<GenreDto> getAllGenres() {
        return genreRepository.findAll()
                .map(GenreDto::toDto);
    }

    @DeleteMapping("/api/genres/{id}")
    public ResponseEntity<Genre> deleteGenreById(@PathVariable("id") Long id) {
        genreService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/genres/{id}")
    public GenreDto getGenreById(@PathVariable("id") Long id) {
        return GenreDto.toDto(genreService.getById(id));
    }

    @PatchMapping("/api/genres/{id}")
    public ResponseEntity<Genre> updateGenre(GenreDto genre) {
        genreService.update(genre.getId(), genre.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/genres")
    public ResponseEntity<Genre> createGenre(GenreDto genre) {
        genreService.create(genre.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
