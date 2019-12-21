package pl.com.app.filersAndSort;


import java.util.List;

public interface SortByMovieValues<T> {

    List<T> sortByTitle(List<T> movies);
    List<T> sortByGenre(List<T> movies);
    List<T> sortByPrice(List<T> movies);
    List<T> sortByDuration(List<T> movies);
    List<T> sortByReleaseDate(List<T> movies);
}
