package ru.otus.libraryapplication.controller.genre;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.libraryapplication.dto.AuthorDto;
import ru.otus.libraryapplication.dto.GenreDto;
import ru.otus.libraryapplication.service.genre.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/genres")
    public String getAllGenres(Model model) {
        List<GenreDto> genres = genreService.getAll();
        model.addAttribute("genres", genres);
        return "genres";
    }

    @GetMapping("/genres/delete")
    public String deleteGenreById(@RequestParam("id") long id, Model model) {
        genreService.deleteById(id);
        return "redirect:/genres";
    }

    @GetMapping("/genres/edit")
    public String editPage(@RequestParam("id") Long id, Model model) {
        GenreDto genre = genreService.getById(id);
        model.addAttribute("genre", genre);
        return "editGenre";
    }

    @PostMapping("/genres/edit")
    public String updateGenre(GenreDto genre) {
        genreService.update(genre);
        return "redirect:/genres";
    }

    @GetMapping("/genres/create")
    public String createPage() {
        return "createGenre";
    }

    @PostMapping("/genres/create")
    public String createGenre(GenreDto genre) {
        genreService.create(genre);
        return "redirect:/genres";
    }
}
