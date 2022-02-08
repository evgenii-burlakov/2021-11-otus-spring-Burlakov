package ru.otus.libraryapplication.controller.genre;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.otus.libraryapplication.service.genre.GenreService;

@Controller
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;
}
