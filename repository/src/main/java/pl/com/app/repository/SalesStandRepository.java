package pl.com.app.repository;

import pl.com.app.model.SalesStand;
import pl.com.app.sqlbuilder.creator.SqlInsertCreator;

import java.sql.Timestamp;

public interface SalesStandRepository extends CrudRepository<SalesStand, Integer> {

    void deleteByCustom(String columnName, Object value);

    static String getInsertSql(SalesStand item){
        return new SqlInsertCreator
                .SqlInsertBuilder()
                .tableName("sales_stand")
                .addCondition("customer_id", item.getCustomerId())
                .addCondition("movie_id", item.getMovieId())
                .addCondition("start_date_time", Timestamp.valueOf(item.getStartDateTime()))
                .build();
    }

    Integer getInsertedId();
}
