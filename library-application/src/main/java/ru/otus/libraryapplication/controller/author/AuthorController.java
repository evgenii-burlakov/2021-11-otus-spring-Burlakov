package ru.otus.libraryapplication.controller.author;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.dto.AuthorDto;
import ru.otus.libraryapplication.repositories.author.AuthorRepository;
import ru.otus.libraryapplication.service.author.AuthorService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorRepository authorRepository;

//    @GetMapping("/api/authors")
//    public Flux<AuthorDto> getAllAuthors() {
//        return authorRepository.findAll()
//                .map(AuthorDto::toDto);
//    }

    @DeleteMapping("/api/authors/{id}")
    public ResponseEntity<Author> deleteAuthorById(@PathVariable("id") Long id) {
        authorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/api/authors/{id}")
//    public AuthorDto getAuthorById(@PathVariable("id") Long id) {
//        return AuthorDto.toDto(authorService.getById(id));
//    }

    @PatchMapping("/api/authors/{id}")
    public ResponseEntity<Author> updateAuthor(AuthorDto author) {
        authorService.update(author.getId(), author.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/authors")
    public ResponseEntity<Author> createAuthor(AuthorDto author) {
        authorService.create(author.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
