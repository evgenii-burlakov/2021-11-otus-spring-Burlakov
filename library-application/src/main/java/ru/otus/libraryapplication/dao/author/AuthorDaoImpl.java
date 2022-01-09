package ru.otus.libraryapplication.dao.author;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.libraryapplication.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {
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
        return jdbc.queryForObject(sql, params, new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update(
                "delete from authors where id = :id", params
        );
    }

    @Override
    public void update(long id, String name) {
        Map<String, Object> params = Map.of("id", id, "name", name);
        jdbc.update(
                "update authors set name = :name where id = :id", params
        );
    }

    @Override
    public void create(String name) {
        Map<String, Object> params = Map.of("name", name);
        jdbc.update(
                "insert into authors(name) values (:name)", params
        );
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
