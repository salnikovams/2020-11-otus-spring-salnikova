package ru.otus.spring.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookDaoJdbc implements BookDao {

    private final AuthorDao authorDao ;
    private final GenreDao genreDao;

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations,  AuthorDao authorDao, GenreDao genreDao)
    {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public int count() {
        return namedParameterJdbcOperations.queryForObject("select count(*) from Book", Collections.EMPTY_MAP, Integer.class);
    }

    @Override
    public Long insert(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        params.addValue("name", book.getName());
        params.addValue("authorId", book.getAuthor().getId());
        params.addValue("genreId", book.getGenre().getId());
        namedParameterJdbcOperations.update("insert into Book ( `name`, authorId, genreId) values (:name, :authorId, :genreId)", params, keyHolder, new String[] { "id" });
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Book book) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", book.getId());
        params.put("name", book.getName());
        params.put("authorId", book.getAuthor().getId());
        params.put("genreId", book.getGenre().getId());
        namedParameterJdbcOperations.update("update Book set `name`= :name, authorId = :authorId, genreId= :genreId where id =:id", params);
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
             return namedParameterJdbcOperations.queryForObject(
                     "select * from Book where id = :id", params, new BookMapper()
                );
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Book getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        try {
            return namedParameterJdbcOperations.queryForObject(
                    "select * from Book b where b.name = :name", params, new BookMapper()
            );
        }
        catch (DataAccessException e) {
                return null;
            }
    }

    @Override
    public List<Book> getAll() {
        return namedParameterJdbcOperations.query("select * from Book", new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from book where id = :id", params
        );
    }

    private  class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Author author  = authorDao.getById(resultSet.getLong("authorId"));
            Genre genre  = genreDao.getById(resultSet.getLong("genreId"));
            return new Book(id, name, author, genre);
        }
    }
}
