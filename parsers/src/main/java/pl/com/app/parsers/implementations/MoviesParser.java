package pl.com.app.parsers.implementations;

import pl.com.app.model.Movie;
import pl.com.app.parsers.interfaces.Parser;
import pl.com.app.parsers.interfaces.RegularExpressions;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MoviesParser implements Parser<Movie> {

    @Override
    public Movie parse(String line) {
        String[] arr = Parser.splitLine(line, RegularExpressions.MOVIES, ",", "MOVIE");

        return Movie.builder()
                .title(arr[0])
                .genre(arr[1])
                .price(new BigDecimal(arr[2]))
                .duration(Integer.valueOf(arr[3]))
                .releaseDate(LocalDate.parse(arr[4]))
                .build();
    }
}
