package ru.otus.spring.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations)
    {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return namedParameterJdbcOperations.queryForObject("select count(*) from Author", Collections.EMPTY_MAP, Integer.class);
    }

    @Override
    public Author insert(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        params.addValue("name", author.getName());
        namedParameterJdbcOperations.update("insert into Author ( `name`) values (:name)", params, keyHolder, new String[] { "id" });
        author.setId(keyHolder.getKey().longValue());
        return author;
    }

    @Override
    public void update(Author author) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", author.getId());
        params.put("name", author.getName());
        namedParameterJdbcOperations.update("update Author set `name`=:name where id =:id", params);
    }

    @Override
    public Author getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return namedParameterJdbcOperations.queryForObject(
                    "select ID, NAME from Author where id = :id", params, new AuthorMapper()
            );
        } catch (DataAccessException e) {
            return null;
        }

    }

    @Override
    public List<Author> getAll() {
        return namedParameterJdbcOperations.query("select ID, NAME from Author", new AuthorMapper());

    }

    @Override
    public Author getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        try {
            return namedParameterJdbcOperations.queryForObject("select ID, NAME from Author a where a.name = :name", params, new AuthorMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from Author where id = :id", params
        );
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}
