package ru.otus.libraryapplication.service;

import org.mockito.Mockito;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.libraryapplication.dao.author.AuthorDao;
import ru.otus.libraryapplication.dao.book.BookDao;
import ru.otus.libraryapplication.dao.genre.GenreDao;

@SpringBootConfiguration
@ComponentScan("ru.otus.libraryapplication.service")
public class TestSpringBootServiceConfiguration {
    @Bean
    public AuthorDao authorDao() {
        return Mockito.mock(AuthorDao.class);
    }

    @Bean
    public GenreDao genreDao() {
        return Mockito.mock(GenreDao.class);
    }

    @Bean
    public BookDao bookDao() {
        return Mockito.mock(BookDao.class);
    }
}