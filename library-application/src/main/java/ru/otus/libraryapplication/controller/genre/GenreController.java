package ru.otus.libraryapplication.controller.genre;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.libraryapplication.dto.GenreDto;
import ru.otus.libraryapplication.service.genre.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/genres")
    public List<GenreDto> getAllGenres(Model model) {
        return genreService.getAll().stream()
                .map(GenreDto::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/genres")
    public ResponseEntity deleteGenreById(@RequestParam("id") long id) {
        genreService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/genres/{id}")
    public GenreDto editGenre(@PathVariable("id") Long id) {
        return GenreDto.toDto(genreService.getById(id));
    }

    @PatchMapping("/genres/{id}")
    public ResponseEntity updateGenre(GenreDto genre) {
        genreService.update(genre.getId(), genre.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/genres")
    public ResponseEntity createGenre(GenreDto genre) {
        genreService.create(genre.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
