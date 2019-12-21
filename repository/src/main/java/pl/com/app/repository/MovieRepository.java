package pl.com.app.repository;


import pl.com.app.model.Movie;
import pl.com.app.sqlbuilder.creator.SqlInsertCreator;

import java.sql.Date;
import java.util.List;


public interface MovieRepository extends CrudRepository<Movie, Integer> {

    List<Movie> findByCustom(String columnName, Object value);
    void updateAll(String columnName, Object columnValue);

    static String getInsertSql(Movie item){
        return new SqlInsertCreator
                .SqlInsertBuilder()
                .tableName("movie")
                .addCondition("title", item.getTitle())
                .addCondition("genre", item.getGenre())
                .addCondition("price", item.getPrice())
                .addCondition("duration", item.getDuration())
                .addCondition("release_date", Date.valueOf(item.getReleaseDate()))
                .build();
    }

}
