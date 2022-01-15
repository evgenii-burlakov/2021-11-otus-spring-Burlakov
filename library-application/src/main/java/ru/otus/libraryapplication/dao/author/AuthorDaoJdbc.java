package ru.otus.libraryapplication.dao.author;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.libraryapplication.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Author> getAll() {
        String sql = "select id, name " +
                "from authors ";
        return jdbc.query(sql, new AuthorMapper());
    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        String sql = "select id, name " +
                "from authors " +
                "where id = :id";
        return DataAccessUtils.singleResult(jdbc.query(sql, params, new AuthorMapper()));
    }

    @Override
    public Author getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        String sql = "select id, name " +
                "from authors " +
                "where name = :name";
        return DataAccessUtils.singleResult(jdbc.query(sql, params, new AuthorMapper()));
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from authors where id = :id", params);
    }

    @Override
    public void update(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", author.getId());
        params.addValue("name", author.getName());

        jdbc.update("update authors set name = :name where id = :id", params);
    }

    @Override
    public long create(String name) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);

        KeyHolder kh = new GeneratedKeyHolder();

        jdbc.update("insert into authors(name) values (:name)", params, kh);

        return kh.getKey().longValue();
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long authorId = resultSet.getLong("id");
            String authorName = resultSet.getString("name");
            return new Author(authorId, authorName);
        }
    }
}
