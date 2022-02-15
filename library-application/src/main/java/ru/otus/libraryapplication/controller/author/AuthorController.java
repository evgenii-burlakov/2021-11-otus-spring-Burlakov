package ru.otus.libraryapplication.controller.author;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.libraryapplication.dto.AuthorDto;
import ru.otus.libraryapplication.service.author.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/authors")
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAll().stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/authors")
    public ResponseEntity deleteAuthorById(@RequestParam("id") long id) {
        authorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/authors/{id}")
    public AuthorDto getAuthor(@PathVariable("id") Long id) {
        return AuthorDto.toDto(authorService.getById(id));
    }

    @PatchMapping("/authors/{id}")
    public ResponseEntity updateAuthor(AuthorDto author) {
        authorService.update(author.getId(), author.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/authors")
    public ResponseEntity createAuthor(AuthorDto author) {
        authorService.create(author.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
