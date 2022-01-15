package ru.otus.libraryapplication.dao.book;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
public class BookDaoJdbc implements BookDao {

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

        return DataAccessUtils.singleResult(jdbc.query(sql, params, new BookMapper()));
    }

    @Override
    public boolean isEqualBookExist(String bookName, String author, String genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("bookName", bookName);
        params.addValue("author", author);
        params.addValue("genre", genre);

        String sql = "select count(*) " +
                "from books b " +
                "inner join authors a on b.author_id=a.id " +
                "inner join genres g on b.genre_id=g.id " +
                "where b.name = :bookName " +
                "and a.name = :author " +
                "and g.name = :genre";

        Integer count = jdbc.queryForObject(sql, params, Integer.class);
        return count != 0;
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from books where id = :id", params);
    }

    @Override
    public long create(String bookName, Author bookAuthor, Genre bookGenre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", bookName);
        params.addValue("author_id", bookAuthor.getId());
        params.addValue("genre_id", bookGenre.getId());

        KeyHolder kh = new GeneratedKeyHolder();

        jdbc.update("insert into books(NAME, AUTHOR_ID, GENRE_ID) values (:name, :author_id, :genre_id)", params, kh);

        return kh.getKey().longValue();
    }

    @Override
    public void update(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("name", book.getName());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());
        jdbc.update("update books set name = :name, author_id = :author_id, genre_id = :genre_id where id = :id", params);
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
