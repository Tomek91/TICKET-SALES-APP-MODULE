package pl.com.app.repository;

import pl.com.app.model.LoyaltyCard;
import pl.com.app.sqlbuilder.creator.SqlInsertCreator;

import java.sql.Date;

public interface LoyaltyCardRepository extends CrudRepository<LoyaltyCard, Integer> {

    static String getInsertSql(LoyaltyCard item){
        return  new SqlInsertCreator
                .SqlInsertBuilder()
                .tableName("loyalty_card")
                .addCondition("expiration_date", Date.valueOf(item.getExpirationDate()))
                .addCondition("discount", item.getDiscount())
                .addCondition("movies_number", item.getMoviesNumber())
                .build();
    }
}
