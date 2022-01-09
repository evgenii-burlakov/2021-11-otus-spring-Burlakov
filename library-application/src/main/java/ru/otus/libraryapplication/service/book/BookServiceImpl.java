package ru.otus.libraryapplication.service.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.libraryapplication.dao.book.BookDao;
import ru.otus.libraryapplication.domain.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao dao;

    @Override

    public List<Book> getAll() {
        return dao.getAll();
    }

    @Override
    public Book getById(long id) {
        return dao.getById(id);
    }

    @Override
    public void deleteById(long id) {
        dao.deleteById(id);
    }

    @Override
    public void updateBookName(long id, String name) {
        dao.updateBookName(id, name);
    }
}
