package pl.com.app.service;

import pl.com.app.connection.DbConnection;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.Movie;
import pl.com.app.model.MovieSalesStand;
import pl.com.app.repository.MovieRepository;
import pl.com.app.repository.MovieRepositoryImpl;
import pl.com.app.repository.SalesStandRepository;
import pl.com.app.repository.SalesStandRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieSalesStandServiceImpl implements MovieSalesStandService {

    private SalesStandRepository salesStandRepository = new SalesStandRepositoryImpl();
    private MovieRepository movieRepository = new MovieRepositoryImpl();

    @Override
    public List<MovieSalesStand> findByCustomerId(Integer customerId) {
        List<MovieSalesStand> movieSalesStands = new ArrayList<>();
        try {
            if (customerId == null ) {
                throw new NullPointerException("CUSTOMER_ID IS NULL");
            }
            movieSalesStands = salesStandRepository.findAll()
                    .stream()
                    .filter(x -> x.getCustomerId() == customerId)
                    .map(x -> {
                        Movie movie = movieRepository
                                .findOneById(x.getMovieId())
                                .orElseThrow(NullPointerException::new);
                        return MovieSalesStand.builder().movie(movie).salesStand(x).build();
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new MyException("MOVIE_SALES_STAND SERVICE, FIND BY CUSTOMER ID");
        }
        return movieSalesStands;
    }

    @Override
    public void deleteAll() {
        try {
            DbConnection.connection().setAutoCommit(false);
            movieRepository.deleteAll();
            salesStandRepository.deleteAll();

            DbConnection.connection().commit();
            DbConnection.connection().setAutoCommit(true);
        } catch (Exception e) {
            try {
                DbConnection.connection().rollback();
            } catch (Exception ee) {
                throw new MyException("MOVIE_SALES_STAND SERVICE ROLLBACK EXCEPTION");
            }
            throw new MyException("MOVIE_SALES_STAND SERVICE, DELETE ALL");
        }
    }

    @Override
    public void deleteByCustom(String columnName, Object columnValue) {
        try {
            if (columnName == null || columnValue == null) {
                throw new NullPointerException("CONDITION IS NULL");
            }
            DbConnection.connection().setAutoCommit(false);

            List<Movie> movies = movieRepository.findByCustom(columnName, columnValue);
            for (Movie m : movies){
                movieRepository.delete(m.getId());
                salesStandRepository.deleteByCustom("movie_id", m.getId());
            }

            DbConnection.connection().commit();
            DbConnection.connection().setAutoCommit(true);
        } catch (Exception e) {
            try {
                DbConnection.connection().rollback();
            } catch (Exception ee) {
                throw new MyException("MOVIE_SALES_STAND SERVICE, ROLLBACK EXCEPTION");
            }
            throw new MyException("MOVIE_SALES_STAND SERVICE, DELETE BY CUSTOM");
        }
    }
}
