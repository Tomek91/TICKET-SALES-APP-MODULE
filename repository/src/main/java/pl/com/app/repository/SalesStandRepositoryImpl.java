package pl.com.app.repository;


import pl.com.app.connection.DbConnection;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.SalesStand;
import pl.com.app.sqlbuilder.creator.SqlDeleteCreator;
import pl.com.app.sqlbuilder.creator.SqlSelectCreator;
import pl.com.app.sqlbuilder.creator.SqlUpdateCreator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SalesStandRepositoryImpl implements SalesStandRepository {

    Connection connection = DbConnection.getInstance().getConnection();

    @Override
    public void add(SalesStand item) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(SalesStandRepository.getInsertSql(item));
        } catch (Exception e) {
            throw new MyException("SALES STAND REPOSITORY, ADD");
        }
    }

    @Override
    public void addAll(List<SalesStand> items) {
        try {
            Statement statement = connection.createStatement();
            for(SalesStand item : items){
                statement.addBatch(SalesStandRepository.getInsertSql(item));
            }
            statement.executeBatch();
        } catch (Exception e) {
            throw new MyException("SALES STAND REPOSITORY, ADD ALL");
        }
    }

    @Override
    public void update(SalesStand item) {
        try {
            final String updateSql = new SqlUpdateCreator
                    .SqlUpdateBuilder()
                    .tableName("sales_stand")
                    .addSetClause("customer_id", item.getCustomerId())
                    .addSetClause("movie_id", item.getMovieId())
                    .addSetClause("start_date_time", Timestamp.valueOf(item.getStartDateTime()))
                    .addCondition("id", item.getId())
                    .build();

            Statement statement = connection.createStatement();
            statement.execute(updateSql);
        } catch (Exception e) {
            throw new MyException("SALES STAND REPOSITORY, UPDATE");
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            final String deleteSql = new SqlDeleteCreator
                    .SqlDeleteBuilder()
                    .tableName("sales_stand")
                    .addCondition("id", id)
                    .build();

            Statement statement = connection.createStatement();
            statement.execute(deleteSql);
        } catch (Exception e) {
            throw new MyException("SALES STAND REPOSITORY, DELETE");
        }
    }

    @Override
    public void deleteAll() {
        try {
            final String deleteAllSql = new SqlDeleteCreator
                    .SqlDeleteBuilder()
                    .tableName("sales_stand")
                    .build();

            Statement statement = connection.createStatement();
            statement.execute(deleteAllSql);
        } catch (Exception e) {
            throw new MyException("SALES STAND REPOSITORY, DELETE ALL");
        }
    }

    @Override
    public Optional<SalesStand> findOneById(Integer id) {
        Optional<SalesStand> op = Optional.empty();
        try {
            final String selectSql = new SqlSelectCreator
                    .SqlSelectBuilder()
                    .tableName("sales_stand")
                    .addCondition("id", id)
                    .build();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectSql);
            if(rs.next()){
                op = Optional.of(new SalesStand(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getInt("movie_id"),
                        LocalDateTime.parse(rs.getString("start_date_time")))
                );
            }
        } catch (Exception e) {
            throw new MyException("SALES STAND REPOSITORY, FIND ONE BY ID");
        }
        return op;
    }

    @Override
    public List<SalesStand> findAll() {

        List<SalesStand> salesStands = new ArrayList<>();
        try {
            final String selectSql = new SqlSelectCreator
                    .SqlSelectBuilder()
                    .tableName("sales_stand")
                    .build();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectSql);
            while(rs.next()){

                salesStands.add(new SalesStand(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getInt("movie_id"),
                        LocalDateTime.parse(rs.getString("start_date_time"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s")))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("SALES STAND REPOSITORY, FIND ALL");
        }
        return salesStands;
    }

    @Override
    public void deleteByCustom(String columnName, Object value) {
        try {
            final String deleteSql = new SqlDeleteCreator
                    .SqlDeleteBuilder()
                    .tableName("sales_stand")
                    .addCondition( columnName, value)
                    .build();

            Statement statement = connection.createStatement();
            statement.execute(deleteSql);
        } catch (Exception e) {
            throw new MyException("SALES STAND REPOSITORY, DELETE BY CUSTOM");
        }
    }

    @Override
    public Integer getInsertedId() {
        Integer insertedId = null;
        try {
            final String selectSql = "SELECT last_insert_rowid();";

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectSql);
            if(rs.next()){
                insertedId = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("SALES STAND REPOSITORY, FIND ALL");
        }
        return insertedId;
    }
}