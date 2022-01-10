package ru.otus.libraryapplication.dao.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.libraryapplication.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Genre> getAll() {
        String sql = "select id, name " +
                "from genres ";
        return jdbc.query(sql, new GenreMapper());
    }

    @Override
    public Genre getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        String sql = "select id, name " +
                "from genres " +
                "where id = :id";
        return DataAccessUtils.singleResult(jdbc.query(sql, params, new GenreMapper()));
    }

    @Override
    public Genre getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        String sql = "select id, name " +
                "from genres " +
                "where name = :name";
        return DataAccessUtils.singleResult(jdbc.query(sql, params, new GenreMapper()));
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update(
                "delete from genres where id = :id", params
        );
    }

    @Override
    public void update(long id, String name) {
        Map<String, Object> params = Map.of("id", id, "name", name);
        jdbc.update(
                "update genres set name = :name where id = :id", params
        );
    }

    @Override
    public long create(String name) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);

        KeyHolder kh = new GeneratedKeyHolder();

        jdbc.update("insert into genres(name) values (:name)", params, kh);

        return kh.getKey().longValue();
    }

    @Override
    public List<Long> getUniqueGenresToAuthor(long authorId) {
        Map<String, Object> params = Collections.singletonMap("author_id", authorId);
        String sql = "select genre_id  " +
                "from books " +
                "group by genre_id " +
                "having count(distinct author_id) = 1 " +
                "and genre_id = any (select genre_id from books where author_id = :author_id)";
        return jdbc.query(sql, params, (rs, rowNum) -> rs.getLong("genre_id"));
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
