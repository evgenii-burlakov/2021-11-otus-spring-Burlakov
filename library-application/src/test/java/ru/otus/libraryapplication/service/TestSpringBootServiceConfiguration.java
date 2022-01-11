package ru.otus.libraryapplication.service;

import org.mockito.Mockito;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.libraryapplication.dao.author.AuthorDao;
import ru.otus.libraryapplication.dao.genre.GenreDao;
import ru.otus.libraryapplication.service.author.AuthorService;
import ru.otus.libraryapplication.service.author.AuthorServiceImpl;
import ru.otus.libraryapplication.service.string.StringService;
import ru.otus.libraryapplication.service.string.StringServiceImpl;

@SpringBootConfiguration
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
    public StringService stringService() {
        return new StringServiceImpl();
    }

    @Bean
    public AuthorService authorService() {
        return new AuthorServiceImpl(authorDao(), genreDao(), stringService());
    }
}
