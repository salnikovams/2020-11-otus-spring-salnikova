package ru.otus.spring.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations)
    {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return namedParameterJdbcOperations.queryForObject("select count(*) from Genre", Collections.EMPTY_MAP, Integer.class);
    }

    @Override
    public Genre insert(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        params.addValue("name", genre.getName());
        namedParameterJdbcOperations.update("insert into Genre (`name`) values (:name)", params, keyHolder, new String[] { "id" });
        genre.setId(keyHolder.getKey().longValue());
        return genre;
    }

    @Override
    public void update(Genre genre) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", genre.getId());
        params.put("name", genre.getName());
        namedParameterJdbcOperations.update("update Genre set `name`=:name where id = :id", params);
    }

    @Override
    public Genre getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
             return namedParameterJdbcOperations.queryForObject(
                    "select * from Genre where id = :id", params, new GenreMapper()
            );
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Genre> getAll() {

        return namedParameterJdbcOperations.query("select * from Genre", new GenreMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from Genre where id = :id", params
        );
    }

    @Override
    public Genre getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        try {
            return namedParameterJdbcOperations.queryForObject("select distinct * from Genre a where a.name = :name", params, new GenreDaoJdbc.GenreMapper());
        } catch (DataAccessException e) {
            return null;
        }
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
