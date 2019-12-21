package pl.com.app.repository;

import pl.com.app.connection.DbConnection;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.Customer;
import pl.com.app.sqlbuilder.creator.SqlDeleteCreator;
import pl.com.app.sqlbuilder.creator.SqlSelectCreator;
import pl.com.app.sqlbuilder.creator.SqlUpdateCreator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepositoryImpl implements CustomerRepository {

    Connection connection = DbConnection.getInstance().getConnection();

    @Override
    public void add(Customer item) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(CustomerRepository.getInsertSql(item));
        } catch (Exception e) {
            throw new MyException("CUSTOMER REPOSITORY, ADD");
        }
    }

    @Override
    public void addAll(List<Customer> items) {
        try {
            Statement statement = connection.createStatement();
            for(Customer item : items){
                statement.addBatch(CustomerRepository.getInsertSql(item));
            }
            statement.executeBatch();
        } catch (Exception e) {
            throw new MyException("CUSTOMER REPOSITORY, ADD ALL");
        }
    }

    @Override
    public void update(Customer item) {
        try {
            final String updateSql = new SqlUpdateCreator
                    .SqlUpdateBuilder()
                    .tableName("customer")
                    .addSetClause("name", item.getName())
                    .addSetClause("surname", item.getSurname())
                    .addSetClause("age", item.getAge())
                    .addSetClause("email", item.getEmail())
                    .addSetClause("loyalty_card_id", item.getLoyaltyCardId())
                    .addCondition("id", item.getId())
                    .build();

            Statement statement = connection.createStatement();
            statement.execute(updateSql);
        } catch (Exception e) {
            throw new MyException("CUSTOMER REPOSITORY, UPDATE");
        }
    }

    @Override
    public void updateAll(String columnName, Object columnValue) {
        try {
            final String updateSql = new SqlUpdateCreator
                    .SqlUpdateBuilder()
                    .tableName("customer")
                    .addSetClause(columnName, columnValue)
                    .build();

            Statement statement = connection.createStatement();
            statement.execute(updateSql);
        } catch (Exception e) {
            throw new MyException("CUSTOMER REPOSITORY, UPDATE ALL");
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            final String deleteSql = new SqlDeleteCreator
                    .SqlDeleteBuilder()
                    .tableName("customer")
                    .addCondition("id", id)
                    .build();

            Statement statement = connection.createStatement();
            statement.execute(deleteSql);
        } catch (Exception e) {
            throw new MyException("CUSTOMER REPOSITORY, DELETE");
        }
    }

    @Override
    public void deleteAll() {
        try {
            final String deleteAllSql = new SqlDeleteCreator
                    .SqlDeleteBuilder()
                    .tableName("customer")
                    .build();

            Statement statement = connection.createStatement();
            statement.execute(deleteAllSql);
        } catch (Exception e) {
            throw new MyException("CUSTOMER REPOSITORY, DELETE ALL");
        }
    }

    @Override
    public Optional<Customer> findOneById(Integer id) {
        Optional<Customer> op = Optional.empty();
        try {
            final String selectSql = new SqlSelectCreator
                    .SqlSelectBuilder()
                    .tableName("customer")
                    .addCondition("id", id)
                    .build();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectSql);
            if(rs.next()){
               op = Optional.of(new Customer(
                       rs.getInt("id"),
                       rs.getString("name"),
                       rs.getString("surname"),
                       rs.getInt("age"),
                       rs.getString("email"),
                       rs.getInt("loyalty_card_id"))
               );
            }
        } catch (Exception e) {
            throw new MyException("CUSTOMER REPOSITORY, FIND ONE BY ID");
        }
        return op;
    }

    @Override
    public List<Customer> findAll() {

        List<Customer> customers = new ArrayList<>();
        try {
            final String selectSql = new SqlSelectCreator
                    .SqlSelectBuilder()
                    .tableName("customer")
                    .build();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectSql);
            while(rs.next()){
                customers.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("age"),
                        rs.getString("email"),
                        rs.getInt("loyalty_card_id"))
                );
            }
        } catch (Exception e) {
            throw new MyException("CUSTOMER REPOSITORY, FIND ALL");
        }
        return customers;
    }

    @Override
    public Optional<Customer> findOneByLoyaltyCardId(Integer loaltyCardId) {
        Optional<Customer> op = Optional.empty();
        try {
            final String selectSql = new SqlSelectCreator
                    .SqlSelectBuilder()
                    .tableName("customer")
                    .addCondition("loyalty_card_id", loaltyCardId)
                    .build();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectSql);
            if(rs.next()){
                op = Optional.of(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("age"),
                        rs.getString("email"),
                        rs.getInt("loyalty_card_id"))
                );
            }
        } catch (Exception e) {
            throw new MyException("CUSTOMER REPOSITORY, FIND ONE BY LOYALTY CARD ID");
        }
        return op;
    }

    @Override
    public List<Customer> findByCustom(String columnName, Object value) {
        List<Customer> customers = new ArrayList<>();
        try {
            final String selectSql = new SqlSelectCreator
                    .SqlSelectBuilder()
                    .tableName("customer")
                    .addCondition(columnName, value)
                    .build();


            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectSql);
            while(rs.next()){
                customers.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("age"),
                        rs.getString("email"),
                        rs.getInt("loyalty_card_id"))
                );
            }
        } catch (Exception e) {
            throw new MyException("CUSTOMER REPOSITORY, FIND BY CUSTOM");
        }
        return customers;
    }

    @Override
    public Optional<Customer> findByNameSurnameAndEmail(String name, String surname, String email) {
        Optional<Customer> op = Optional.empty();
        try {
            final String selectSql = new SqlSelectCreator
                    .SqlSelectBuilder()
                    .tableName("customer")
                    .addCondition("name", name)
                    .addCondition("surname", surname)
                    .addCondition("email", email)
                    .build();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectSql);
            if(rs.next()){
                op = Optional.of(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getInt("age"),
                        rs.getString("email"),
                        rs.getInt("loyalty_card_id"))
                );
            }
        } catch (Exception e) {
            throw new MyException("CUSTOMER REPOSITORY, FIND BY NAME SURNAME AND EMAIL");
        }
        return op;
    }
}