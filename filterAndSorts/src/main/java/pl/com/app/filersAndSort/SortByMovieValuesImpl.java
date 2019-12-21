package pl.com.app.filersAndSort;

import pl.com.app.exceptions.MyException;
import pl.com.app.model.Movie;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortByMovieValuesImpl implements SortByMovieValues<Movie> {

    @Override
    public List<Movie> sortByTitle(List<Movie> movies) {
        List<Movie> movieList = new ArrayList<>();
        try{
            if (movies == null) {
                throw new NullPointerException("MOVIES IS NULL");
            }
            movieList = movies.stream()
                    .sorted(Comparator.comparing(Movie::getTitle))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new MyException("MOVIE, SORT BY TITLE");
        }
        return movieList;
    }

    @Override
    public List<Movie> sortByGenre(List<Movie> movies) {
        List<Movie> movieList = new ArrayList<>();
        try{
            if (movies == null) {
                throw new NullPointerException("MOVIES IS NULL");
            }
            movieList = movies.stream()
                    .sorted(Comparator.comparing(Movie::getGenre))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new MyException("MOVIE, SORT BY GENRE");
        }
        return movieList;
    }

    @Override
    public List<Movie> sortByPrice(List<Movie> movies) {
        List<Movie> movieList = new ArrayList<>();
        try{
            if (movies == null) {
                throw new NullPointerException("MOVIES IS NULL");
            }
            movieList = movies.stream()
                    .sorted(Comparator.comparing(Movie::getPrice))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new MyException("MOVIE, SORT BY PRICE");
        }
        return movieList;
    }

    @Override
    public List<Movie> sortByDuration(List<Movie> movies) {
        List<Movie> movieList = new ArrayList<>();
        try{
            if (movies == null) {
                throw new NullPointerException("MOVIES IS NULL");
            }
            movieList = movies.stream()
                    .sorted(Comparator.comparing(Movie::getDuration))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new MyException("MOVIE, SORT BY DURATION");
        }
        return movieList;
    }

    @Override
    public List<Movie> sortByReleaseDate(List<Movie> movies) {
        List<Movie> movieList = new ArrayList<>();
        try{
            if (movies == null) {
                throw new NullPointerException("MOVIES IS NULL");
            }
            movieList = movies.stream()
                    .sorted(Comparator.comparing(Movie::getReleaseDate))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new MyException("MOVIE, SORT BY RELEASE DATE");
        }
        return movieList;
    }

}
