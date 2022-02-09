package ru.otus.libraryapplication.service.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.domain.Genre;
import ru.otus.libraryapplication.dto.AuthorDto;
import ru.otus.libraryapplication.dto.BookDto;
import ru.otus.libraryapplication.dto.GenreDto;
import ru.otus.libraryapplication.repositories.book.BookRepository;
import ru.otus.libraryapplication.service.author.AuthorService;
import ru.otus.libraryapplication.service.genre.GenreService;
import ru.otus.libraryapplication.service.string.StringService;
import ru.otus.libraryapplication.util.exeption.ApplicationException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final StringService stringService;

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAll() {
        return bookRepository.findAll().stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto getById(long id) {
        return bookRepository.findById(id).map(BookDto::toDto).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(BookDto bookDto) {
        String author = stringService.beautifyStringName(bookDto.getAuthor().getName());
        String genre = stringService.beautifyStringName(bookDto.getGenre().getName());
        String bookName = stringService.beautifyStringName(bookDto.getName());

        if (stringService.verifyNotBlank(bookName, author, genre)) {
            bookRepository.findById(bookDto.getId()).ifPresentOrElse(book -> {
                book.setName(bookName);
                book.setAuthor(getOrCreateAuthor(author));
                book.setGenre(getOrCreateGenre(genre));
                bookRepository.save(book);
            }, () -> {
                throw new ApplicationException("Invalid book id");
            });
        } else {
            throw new ApplicationException("Invalid book parameters");
        }
    }

    @Override
    @Transactional
    public BookDto create(BookDto bookDto) {
        String author = stringService.beautifyStringName(bookDto.getAuthor().getName());
        String genre = stringService.beautifyStringName(bookDto.getGenre().getName());
        String bookName = stringService.beautifyStringName(bookDto.getName());

        if (!stringService.verifyNotBlank(bookName, author, genre)) {
            throw new ApplicationException("Invalid book parameters");
        } else if (bookRepository.existByBookAuthorAndGenreNames(bookName, author, genre)) {
            throw new ApplicationException("Book already exist");
        } else {
            Author bookAuthor = getOrCreateAuthor(author);

            Genre bookGenre = getOrCreateGenre(genre);

            Book book = new Book(null, bookName, bookAuthor, bookGenre);
            return BookDto.toDto(bookRepository.save(book));
        }
    }

    private Genre getOrCreateGenre(String genre) {
        Genre bookGenre = genreService.getByName(genre);
        if (bookGenre == null) {
            return genreService.create(new GenreDto(genre)).toBean();
        }
        return bookGenre;
    }

    private Author getOrCreateAuthor(String author) {
        Author bookAuthor = authorService.getByName(author);
        if (bookAuthor == null) {
            return authorService.create(new AuthorDto(author)).toBean();
        }
        return bookAuthor;
    }
}
