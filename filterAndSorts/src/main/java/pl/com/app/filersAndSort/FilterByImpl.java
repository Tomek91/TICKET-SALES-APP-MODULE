package pl.com.app.filersAndSort;

import pl.com.app.exceptions.MyException;
import pl.com.app.model.MovieSalesStand;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterByImpl implements FilterBy<MovieSalesStand> {

    @Override
    public List<MovieSalesStand> filterByDuration(Integer duration, List<MovieSalesStand> movieSalesStands) {
        List<MovieSalesStand> movieSalesStandList = new ArrayList<>();
        try{
            if (movieSalesStands == null || duration == null) {
                throw new NullPointerException("CONDITIONS IS NULL");
            }
            movieSalesStandList = movieSalesStands
                    .stream()
                    .filter(x -> x.getMovie().getDuration() == duration)
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new MyException("MOVIE SALES STANDS, FILTER BY DURATION");
        }
        return movieSalesStandList;
    }

    @Override
    public List<MovieSalesStand> filterByDates(LocalDate dateFrom, LocalDate dateTo, List<MovieSalesStand> movieSalesStands) {
        List<MovieSalesStand> movieSalesStandList = new ArrayList<>();
        try{
            if (movieSalesStands == null || dateFrom == null || dateTo == null) {
                throw new NullPointerException("CONDITIONS IS NULL");
            }
            movieSalesStandList = movieSalesStands
                    .stream()
                    .filter(x -> x.getSalesStand().getStartDateTime().toLocalDate().isAfter(dateFrom)
                            && x.getSalesStand().getStartDateTime().toLocalDate().isBefore(dateTo))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new MyException("MOVIE SALES STANDS, FILTER BY DATES");
        }
        return movieSalesStandList;
    }

    @Override
    public List<MovieSalesStand> filterByGenre(String genre, List<MovieSalesStand> movieSalesStands) {
        List<MovieSalesStand> movieSalesStandList = new ArrayList<>();
        try{
            if (movieSalesStands == null || genre == null) {
                throw new NullPointerException("CONDITIONS IS NULL");
            }
            movieSalesStandList = movieSalesStands
                    .stream()
                    .filter(x -> x.getMovie().getGenre().equalsIgnoreCase(genre))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new MyException("MOVIE SALES STANDS, FILTER BY GENRE");
        }
        return movieSalesStandList;
    }

    @Override
    public List<MovieSalesStand> filterByAll(String genre, LocalDate dateFrom, LocalDate dateTo, Integer duration, List<MovieSalesStand> movieSalesStands) {
        List<MovieSalesStand> movieSalesStandList = new ArrayList<>();
        try{
            if (movieSalesStands == null || duration == null || genre == null || dateFrom == null || dateTo == null) {
                throw new NullPointerException("CONDITIONS IS NULL");
            }
            movieSalesStandList = movieSalesStands
                    .stream()
                    .filter(x -> x.getMovie().getDuration() >= duration)
                    .filter(x -> x.getMovie().getGenre().equalsIgnoreCase(genre))
                    .filter(x -> x.getSalesStand().getStartDateTime().toLocalDate().isAfter(dateFrom)
                            && x.getSalesStand().getStartDateTime().toLocalDate().isBefore(dateTo))
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new MyException("MOVIE SALES STANDS, FILTER BY ALL");
        }
        return movieSalesStandList;
    }
}
