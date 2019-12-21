package pl.com.app.repository;

import pl.com.app.connection.DbConnection;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.Movie;
import pl.com.app.sqlbuilder.creator.SqlDeleteCreator;
import pl.com.app.sqlbuilder.creator.SqlSelectCreator;
import pl.com.app.sqlbuilder.creator.SqlUpdateCreator;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieRepositoryImpl implements MovieRepository {

    Connection connection = DbConnection.getInstance().getConnection();

    @Override
    public void add(Movie item) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(MovieRepository.getInsertSql(item));
        } catch (Exception e) {
            throw new MyException("MOVIE REPOSITORY, ADD");
        }
    }

    @Override
    public void addAll(List<Movie> items) {
        try {
            Statement statement = connection.createStatement();
            for(Movie item : items){
                statement.addBatch(MovieRepository.getInsertSql(item));
            }
            statement.executeBatch();
        } catch (Exception e) {
            throw new MyException("MOVIE REPOSITORY, ADD ALL");
        }
    }

    @Override
    public void update(Movie item) {
        try {
            final String updateSql = new SqlUpdateCreator
                    .SqlUpdateBuilder()
                    .tableName("movie")
                    .addSetClause("title", item.getTitle())
                    .addSetClause("genre", item.getGenre())
                    .addSetClause("price", item.getPrice())
                    .addSetClause("duration", item.getDuration())
                    .addSetClause("release_date", Date.valueOf(item.getReleaseDate()))
                    .addCondition("id", item.getId())
                    .build();

            Statement statement = connection.createStatement();
            statement.execute(updateSql);
        } catch (Exception e) {
            throw new MyException("MOVIE REPOSITORY, UPDATE");
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            final String deleteSql = new SqlDeleteCreator
                    .SqlDeleteBuilder()
                    .tableName("movie")
                    .addCondition("id", id)
                    .build();

            Statement statement = connection.createStatement();
            statement.execute(deleteSql);
        } catch (Exception e) {
            throw new MyException("MOVIE REPOSITORY, DELETE");
        }
    }

    @Override
    public void deleteAll() {
        try {
            final String deleteAllSql = new SqlDeleteCreator
                    .SqlDeleteBuilder()
                    .tableName("movie")
                    .build();

            Statement statement = connection.createStatement();
            statement.execute(deleteAllSql);
        } catch (Exception e) {
            throw new MyException("MOVIE REPOSITORY, DELETE ALL");
        }
    }

    @Override
    public Optional<Movie> findOneById(Integer id) {
        Optional<Movie> op = Optional.empty();
        try {
            final String selectSql = new SqlSelectCreator
                    .SqlSelectBuilder()
                    .tableName("movie")
                    .addCondition("id", id)
                    .build();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectSql);
            if(rs.next()){
                op = Optional.of(new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getBigDecimal("price"),
                        rs.getInt("duration"),
                        LocalDate.parse(rs.getString("release_date")))
                );
            }
        } catch (Exception e) {
            throw new MyException("MOVIE REPOSITORY, FIND ONE BY ID");
        }
        return op;
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        try {
            final String selectSql = new SqlSelectCreator
                    .SqlSelectBuilder()
                    .tableName("movie")
                    .build();


            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectSql);
            while(rs.next()){
                movies.add(new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getBigDecimal("price"),
                        rs.getInt("duration"),
                        LocalDate.parse(rs.getString("release_date")))
                );
            }
        } catch (Exception e) {
            throw new MyException("MOVIE REPOSITORY, FIND ALL");
        }
        return movies;
    }

    @Override
    public List<Movie> findByCustom(String columnName, Object value) {
        List<Movie> movies = new ArrayList<>();
        try {
            final String selectSql = new SqlSelectCreator
                    .SqlSelectBuilder()
                    .tableName("movie")
                    .addCondition(columnName, value)
                    .build();


            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectSql);
            while(rs.next()){
                movies.add(new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getBigDecimal("price"),
                        rs.getInt("duration"),
                        LocalDate.parse(rs.getString("release_date")))
                );
            }
        } catch (Exception e) {
            throw new MyException("MOVIE REPOSITORY, FIND BY CUSTOM");
        }
        return movies;
    }

    @Override
    public void updateAll(String columnName, Object columnValue) {
        try {
            final String updateSql = new SqlUpdateCreator
                    .SqlUpdateBuilder()
                    .tableName("movie")
                    .addSetClause(columnName, columnValue)
                    .build();

            Statement statement = connection.createStatement();
            statement.execute(updateSql);
        } catch (Exception e) {
            throw new MyException("MOVIE REPOSITORY, UPDATE ALL");
        }
    }
}