package pl.com.app.service.menu;

import pl.com.app.connection.DataReader;
import pl.com.app.model.Movie;
import pl.com.app.parsers.implementations.MoviesParser;
import pl.com.app.parsers.interfaces.Parser;
import pl.com.app.service.MovieService;
import pl.com.app.service.MovieServiceImpl;

import java.util.List;

public class AddMovie {
    private DataReader dataReader = new DataReader();

    public void addMovies() {
        System.out.println("Dodawanie filmu.");
        System.out.println("Podaj nazwe pliku, z którego zostaną zaciągnięte filmy.");
        final String fileName = dataReader.getString();
        List<Movie> movies = Parser.parseFile(fileName, new MoviesParser());
        if (movies.isEmpty()){
            System.out.println("Plik " + fileName + " jest pusty");
        } else {
            MovieService movieService = new MovieServiceImpl();
            movies.forEach(movieService::addMovie);
            System.out.println("Filmy zostały dodane.");
        }
    }
}
