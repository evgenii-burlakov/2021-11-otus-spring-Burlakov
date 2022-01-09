package ru.otus.libraryapplication.dao.book;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.libraryapplication.domain.Author;
import ru.otus.libraryapplication.domain.Book;
import ru.otus.libraryapplication.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Book> getAll() {
        String sql = "select b.id, b.name as bookName, " +
                "a.id as authorId, a.name as authorName, " +
                "g.id as genreId, g.name as genreName " +
                "from books b " +
                "inner join authors a on b.author_id=a.id " +
                "inner join genres g on b.genre_id=g.id";
        return jdbc.query(sql, new BookMapper());
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        String sql = "select b.id, b.name as bookName, " +
                "a.id as authorId, a.name as authorName, " +
                "g.id as genreId, g.name as genreName " +
                "from books b " +
                "inner join authors a on b.author_id=a.id " +
                "inner join genres g on b.genre_id=g.id " +
                "where b.id = :id";
        return jdbc.queryForObject(sql, params, new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update(
                "delete from books where id = :id", params
        );
    }

    @Override
    public void updateBookName(long id, String name) {
        Map<String, Object> params = Map.of("id", id, "name", name);
        jdbc.update(
                "update books set name = :name where id = :id", params
        );
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long authorId = resultSet.getLong("authorId");
            String authorName = resultSet.getString("authorName");
            Author author = new Author(authorId, authorName);

            long genreId = resultSet.getLong("genreId");
            String genreName = resultSet.getString("genreName");
            Genre genre = new Genre(genreId, genreName);

            long id = resultSet.getLong("id");
            String bookName = resultSet.getString("bookName");
            return new Book(id, bookName, author, genre);
        }
    }
}
