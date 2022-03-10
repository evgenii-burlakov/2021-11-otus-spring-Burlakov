package ru.otus.libraryapplication.service.book;

//@Service
//@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
//    private final BookRepository bookRepository;
//    private final AuthorService authorService;
//    private final GenreService genreService;
//    private final StringService stringService;
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<Book> getAll() {
//        return bookRepository.findAll();
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Book getById(long id) {
//        return bookRepository.findById(id).orElse(null);
//    }
//
//    @Override
//    @Transactional
//    public void deleteById(long id) {
//        bookRepository.deleteById(id);
//    }
//
//    @Override
//    @Transactional
//    public void update(long id, String name, String authorName, String genreName) {
//        String author = stringService.beautifyStringName(authorName);
//        String genre = stringService.beautifyStringName(genreName);
//        String bookName = stringService.beautifyStringName(name);
//
//        if (stringService.verifyNotBlank(bookName, author, genre)) {
//            bookRepository.findById(id).ifPresentOrElse(book -> {
//                book.setName(bookName);
//                book.setAuthor(getOrCreateAuthor(author));
//                book.setGenre(getOrCreateGenre(genre));
//                bookRepository.save(book);
//            }, () -> {
//                throw new ApplicationException("Invalid book id");
//            });
//        } else {
//            throw new ApplicationException("Invalid book parameters");
//        }
//    }
//
//    @Override
//    @Transactional
//    public Book create(String name, String authorName, String genreName) {
//        String author = stringService.beautifyStringName(authorName);
//        String genre = stringService.beautifyStringName(genreName);
//        String bookName = stringService.beautifyStringName(name);
//
//        if (!stringService.verifyNotBlank(bookName, author, genre)) {
//            throw new ApplicationException("Invalid book parameters");
//        } else if (bookRepository.existByBookAuthorAndGenreNames(bookName, author, genre)) {
//            throw new ApplicationException("Book already exist");
//        } else {
//            Author bookAuthor = getOrCreateAuthor(author);
//
//            Genre bookGenre = getOrCreateGenre(genre);
//
//            Book book = new Book(null, bookName, bookAuthor, bookGenre);
//            return bookRepository.save(book);
//        }
//    }
//
//    private Genre getOrCreateGenre(String genre) {
//        Genre bookGenre = genreService.getByName(genre);
//        if (bookGenre == null) {
//            return genreService.create(genre);
//        }
//        return bookGenre;
//    }
//
//    private Author getOrCreateAuthor(String author) {
//        Author bookAuthor = authorService.getByName(author);
//        if (bookAuthor == null) {
//            return authorService.create(author);
//        }
//        return bookAuthor;
//    }
}
