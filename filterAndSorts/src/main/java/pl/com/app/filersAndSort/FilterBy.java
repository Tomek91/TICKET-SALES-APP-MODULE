package pl.com.app.filersAndSort;


import java.time.LocalDate;
import java.util.List;

public interface FilterBy<T> {

    List<T> filterByDuration(Integer duration, List<T> items);
    List<T> filterByDates(LocalDate dateFrom, LocalDate dateTo, List<T> items);
    List<T> filterByGenre(String genre, List<T> items);
    List<T> filterByAll(String genre, LocalDate dateFrom, LocalDate dateTo, Integer duration, List<T> items);
}
