package pl.com.app.service.manage;

import pl.com.app.connection.DataReader;
import pl.com.app.filersAndSort.SortByMovieValues;
import pl.com.app.filersAndSort.SortByMovieValuesImpl;
import pl.com.app.model.Movie;
import pl.com.app.service.MovieSalesStandServiceImpl;
import pl.com.app.service.MovieService;
import pl.com.app.service.MovieServiceImpl;
import pl.com.app.service.menu.Converts;
import pl.com.app.service.menu.TicketSalesSimulator;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MovieManageImpl implements Manage<Movie> {
    private MovieService movieService = new MovieServiceImpl();
    private MovieSalesStandServiceImpl movieSalesStandService = new MovieSalesStandServiceImpl();
    private DataReader dataReader = new DataReader();

    @Override
    public void deleteByCustom() {
        System.out.println("Podaj według jakiego kryterium chcesz usunąć filmy.\nOto dostępne możliwosci:");
        System.out.println(getCriteriums());
        String criterium = dataReader.getString();
        while(!getCriteriums().contains(criterium)){
            System.out.println(TicketSalesSimulator.PREFIX);
            System.out.println("Niepopoprawny kod. Spróbuj jeszcze raz podać kryterium.");
            criterium = dataReader.getString();
        }
        System.out.println(String.join(" ","Podaj wartość dla", criterium, ":"));
        String criteriumValue = dataReader.getString();
        System.out.println("Usunięto filmy według kryterium.");
        movieSalesStandService.deleteByCustom(criterium, criteriumValue);
    }

    @Override
    public void deleteAll() {
        movieSalesStandService.deleteAll();
        System.out.println("Usunięto wszytkie filmy.");
    }

    @Override
    public void updateAll() {
        System.out.println("Modyfikacja wszystkich rekordów:");
        System.out.println("Wykaz wszytkich filmów:");
        List<Movie> movies = movieService.findAll();
        System.out.println(Converts.getOrAbsence(movies));
        if (!movies.isEmpty()) {
            System.out.println("Dostępne możliwosći modyfikacji filmów to:");
            System.out.println(getCriteriums());
            System.out.println("Podaj nazwe kolumny, którą chcesz zmienić wartość (q - kończy modyfikacje)");
            String columnName = null;
            Object columnValue = null;
            do {
                columnName = dataReader.getString();
                columnValue = getColumnValue(columnName);
            } while (!columnName.equals("q") && !getCriteriums().contains(columnName));
            movieService.updateAll(columnName, columnValue);
            System.out.println("Zaktualizowano rekordy");
        } else {
            System.out.println("Nie ma takiego rekordu.");
        }
    }

    private Object getColumnValue(String columnName) {
        switch (columnName){
            case "title":{
                System.out.println("podaj nowy tytuł");
                return dataReader.getString();
            }
            case "genre":{
                System.out.println("podaj nowy gatunek");
                return dataReader.getString();
            }
            case "price":{
                System.out.println("podaj nową cenę biletu");
                return dataReader.getBigDecimal();
            }
            case "duration":{
                System.out.println("podaj nową długość filmu");
                return dataReader.getInteger();
            }
            case "release_date":{
                System.out.println("podaj nową datę premiery");
                return Date.valueOf(dataReader.getLocalDate());
            }
            default:
                System.out.println("niepoprawna nazwa kolumny");
                return null;
        }
    }

    @Override
    public void updateOne() {
        System.out.println("Modyfikacja jednego rekordu:");
        System.out.println("Wykaz wszytkich filmów:");
        List<Movie> movies = movieService.findAll();
        System.out.println(Converts.getOrAbsence(movies));
        if (!movies.isEmpty()){
            System.out.println("Podaj id filmu, którego chcesz modyfikować...");
            Integer id = dataReader.getInteger();
            Optional<Movie> movieOpt = movieService.findById(id);
            if (movieOpt.isPresent()){
                Movie movie = movieOpt.get();
                System.out.println("Dostępne możliwosći modyfikacji to:");
                System.out.println(getCriteriums());
                System.out.println("Podaj nazwe kolumny, którą chcesz zmienić wartość (q - kończy modyfikacje)");
                String columnName = null;
                do {
                    columnName = dataReader.getString();
                    setModificationMovie(columnName, movie);
                }while (!columnName.equals("q") && !getCriteriums().contains(columnName));
                movieService.updateOne(movie);
                System.out.println("Zaktualizowano rekord");
            } else {
                System.out.println("Nie ma takiego rekordu.");
            }
        }
    }

    private void setModificationMovie(String columnName, Movie movie) {
        switch (columnName){
            case "title":{
                System.out.println("podaj nowy tytuł");
                movie.setTitle(dataReader.getString());
                break;
            }
            case "genre":{
                System.out.println("podaj nowy gatunek");
                movie.setGenre(dataReader.getString());
                break;
            }
            case "price":{
                System.out.println("podaj nową cenę biletu");
                BigDecimal price = dataReader.getBigDecimal();
                movie.setPrice(price);
                break;
            }
            case "duration":{
                System.out.println("podaj nową długość filmu");
                Integer duration = dataReader.getInteger();
                movie.setDuration(duration);
                break;
            }
            case "release_date":{
                System.out.println("podaj nową datę premiery");
                LocalDate date = dataReader.getLocalDate();
                movie.setReleaseDate(date);
                break;
            }
            default:
                System.out.println("niepoprawna nazwa kolumny");
                break;
        }
    }

    @Override
    public void findByCustom() {
        System.out.println("Podaj według jakiego kryterium chcesz wyświetlić filmy.\nOto dostępne możliwosci:");
        System.out.println(getCriteriums());
        String criterium = dataReader.getString();
        while(!getCriteriums().contains(criterium)){
            System.out.println(TicketSalesSimulator.PREFIX);
            System.out.println("Niepopoprawny kod. Spróbuj jeszcze raz podać kryterium.");
            criterium = dataReader.getString();
        }
        System.out.println(String.join(" ","Podaj wartość dla", criterium, ":"));
        String criteriumValue = dataReader.getString();
        List<Movie> movies = movieService.findByCustom(criterium, criteriumValue);
        System.out.println("Wykaz filmów według wybranego kryterium:");
        System.out.println(Converts.getOrAbsence(movies));
        if (!movies.isEmpty()){
            System.out.println(TicketSalesSimulator.PREFIX);
            sortBy(movies);
        }
    }

    @Override
    public void findAll() {
        System.out.println("Wykaz wszytkich filmów:");
        List<Movie> movies = movieService.findAll();
        System.out.println(Converts.getOrAbsence(movies));
        sortBy(movies);
    }

    @Override
    public void sortBy (List<Movie> movies){
        System.out.println("Według jakiego kryterium chcesz posortować dane? (q - wyjście)");
        System.out.println(getCriteriums());
        String sort = dataReader.getString();
        SortByMovieValues<Movie> sortByMovieValues = new SortByMovieValuesImpl();
        switch (sort){
            case "title":{
                movies = sortByMovieValues.sortByTitle(movies);
                break;
            }
            case "genre":{
                movies = sortByMovieValues.sortByGenre(movies);
                break;
            }
            case "price":{
                movies = sortByMovieValues.sortByPrice(movies);
                break;
            }
            case "duration":{
                movies = sortByMovieValues.sortByDuration(movies);
                break;
            }
            case "release_date":{
                movies = sortByMovieValues.sortByReleaseDate(movies);
                break;
            }
            case "q":{
                return;
            }
            default:
                System.out.println("Niepopoprawnie wprowadzone dane.");
                return;
        }
        System.out.println(Converts.getOrAbsence(movies));
    }

    @Override
    public String getCriteriums() {
        return String.join("\n",
                "title",
                "genre",
                "price",
                "duration",
                "release_date"
        );
    }
}
