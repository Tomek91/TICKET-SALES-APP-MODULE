package pl.com.app.repository;



import pl.com.app.connection.DbConnection;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.LoyaltyCard;
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

public class LoyaltyCardRepositoryImpl implements LoyaltyCardRepository {

    Connection connection = DbConnection.getInstance().getConnection();

    @Override
    public void add(LoyaltyCard item) {
        try{
            Statement statement = connection.createStatement();
            statement.execute(LoyaltyCardRepository.getInsertSql(item));
        } catch (Exception e) {
            throw new MyException("LOYALTY CARD REPOSITORY, ADD");
        }
    }

    @Override
    public void addAll(List<LoyaltyCard> items) {
        try {
            Statement statement = connection.createStatement();
            for(LoyaltyCard item : items){
                statement.addBatch(LoyaltyCardRepository.getInsertSql(item));
            }
            statement.executeBatch();
        } catch (Exception e) {
            throw new MyException("LOYALTY CARD REPOSITORY, ADD ALL");
        }
    }

    @Override
    public void update(LoyaltyCard item) {
        try {
            final String updateSql = new SqlUpdateCreator
                    .SqlUpdateBuilder()
                    .tableName("loyalty_card")
                    .addSetClause("expiration_date", Date.valueOf(item.getExpirationDate()))
                    .addSetClause("discount", item.getDiscount())
                    .addSetClause("movies_number", item.getMoviesNumber())
                    .addCondition("id", item.getId())
                    .build();

            Statement statement = connection.createStatement();
            statement.execute(updateSql);
        } catch (Exception e) {
            throw new MyException("LOYALTY CARD REPOSITORY, UPDATE");
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            final String deleteSql = new SqlDeleteCreator
                    .SqlDeleteBuilder()
                    .tableName("loyalty_card")
                    .addCondition("id", id)
                    .build();

            Statement statement = connection.createStatement();
            statement.execute(deleteSql);
        } catch (Exception e) {
            throw new MyException("LOYALTY CARD REPOSITORY, DELETE");
        }
    }

    @Override
    public void deleteAll() {
        try {
            final String deleteAllSql = new SqlDeleteCreator
                    .SqlDeleteBuilder()
                    .tableName("loyalty_card")
                    .build();

            Statement statement = connection.createStatement();
            statement.execute(deleteAllSql);
        } catch (Exception e) {
            throw new MyException("LOYALTY CARD REPOSITORY, DELETE ALL");
        }
    }

    @Override
    public Optional<LoyaltyCard> findOneById(Integer id) {
        Optional<LoyaltyCard> op = Optional.empty();
         try {
            final String selectSql = new SqlSelectCreator
                    .SqlSelectBuilder()
                    .tableName("loyalty_card")
                    .addCondition("id", id)
                    .build();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectSql);
            if(rs.next()){
                op = Optional.of(new LoyaltyCard(
                        rs.getInt("id"),
                        LocalDate.parse(rs.getString("expiration_date")),
                        rs.getBigDecimal("discount"),
                        rs.getInt("movies_number"))
                );
            }
        } catch (Exception e) {
            throw new MyException("LOYALTY CARD REPOSITORY, FIND ONE BY ID");
        }
        return op;
    }

    @Override
    public List<LoyaltyCard> findAll() {

        List<LoyaltyCard> loyaltyCards = new ArrayList<>();
        try {
            final String selectSql = new SqlSelectCreator
                    .SqlSelectBuilder()
                    .tableName("loyalty_card")
                    .build();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectSql);
            while(rs.next()){
                loyaltyCards.add(new LoyaltyCard(
                        rs.getInt("id"),
                        LocalDate.parse(rs.getString("expiration_date")),
                        rs.getBigDecimal("discount"),
                        rs.getInt("movies_number"))
                );
            }
        } catch (Exception e) {
            throw new MyException("LOYALTY CARD REPOSITORY, FIND ALL");
        }
        return loyaltyCards;
    }
}
