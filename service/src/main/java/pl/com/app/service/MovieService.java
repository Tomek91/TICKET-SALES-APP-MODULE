package pl.com.app.service;


import pl.com.app.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    void addAll(List<Movie> items);
    void deleteAll();
    void deleteOne(Integer id);
    void updateOne(Movie movie);
    void updateAll(String columnName, Object columnValue);
    Optional<Movie> findById(Integer id);
    List<Movie> findByCustom(String columnName, Object columnValue);
    List<Movie> findAll();
    void addMovie(Movie movie);
    Optional<Movie> theMostProfitMovie();
    Double wholeCinemaProfit();
    Optional<Movie> theNewestMovieWatchMoreThanThree();
    Optional<Movie> theOldestMovieWatchMoreThanThree();
}
