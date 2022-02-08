package ru.otus.libraryapplication.controller.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.libraryapplication.dto.AuthorDto;
import ru.otus.libraryapplication.service.author.AuthorService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/authors")
    public String getAllAuthors(Model model) {
        List<AuthorDto> authors = authorService.getAll();
        model.addAttribute("authors", authors);
        return "authors";
    }

    @GetMapping("/authors/delete")
    public String deleteAuthorById(@RequestParam("id") int id, Model model) {
        authorService.deleteById(id);
        return "redirect:/authors";
    }

    @GetMapping("/authors/edit")
    public String editPage(@RequestParam("id") int id, Model model) {
        AuthorDto author = authorService.getById(id);
        model.addAttribute("author", author);
        return "edit";
    }

    @PostMapping("/authors/edit")
    public String updateAuthor(AuthorDto author) {
        authorService.update(author);
        return "redirect:/authors";
    }

    @PostMapping("/authors/create")
    public String createAuthor(AuthorDto author) {
        authorService.create(author);
        return "redirect:/authors";
    }
}
