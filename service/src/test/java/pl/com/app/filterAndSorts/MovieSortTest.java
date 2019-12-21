package pl.com.app.filterAndSorts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.com.app.filersAndSort.SortByMovieValuesImpl;
import pl.com.app.model.Movie;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MovieSortTest {

    @InjectMocks
    private SortByMovieValuesImpl sortByMovieValues;

    private Random random = new Random();
    private List<Movie> movieList;

    @BeforeEach
    public void init() {
        movieList = List.of(
                Movie.builder().id(random.nextInt(100)).duration(2).genre("horror1").title("FILM1").releaseDate(LocalDate.now().minusDays(1)).price(BigDecimal.TEN).build(),
                Movie.builder().id(random.nextInt(100)).duration(3).genre("horror2").title("FILM2").releaseDate(LocalDate.now().minusDays(11)).price(BigDecimal.ZERO).build(),
                Movie.builder().id(random.nextInt(100)).duration(4).genre("horror3").title("FILM3").releaseDate(LocalDate.now().minusDays(2)).price(BigDecimal.ONE).build(),
                Movie.builder().id(random.nextInt(100)).duration(1).genre("horror4").title("FILM4").releaseDate(LocalDate.now().minusDays(1)).price(BigDecimal.TEN).build()
        );

    }

    @Test
    @DisplayName("sort by title")
    public void test1() {

        List<Movie> movies = sortByMovieValues.sortByTitle(movieList);

        Assertions.assertAll(
                () -> Assertions.assertEquals(4, movies.size()),
                () -> Assertions.assertEquals("FILM1", movies.get(0).getTitle())
        );
    }

    @Test
    @DisplayName("sort by genre")
    public void test2() {

        List<Movie> movies = sortByMovieValues.sortByGenre(movieList);

        Assertions.assertAll(
                () -> Assertions.assertEquals(4, movies.size()),
                () -> Assertions.assertEquals("FILM1", movies.get(0).getTitle())
        );
    }

    @Test
    @DisplayName("sort by price")
    public void test3() {

        List<Movie> movies = sortByMovieValues.sortByPrice(movieList);

        Assertions.assertAll(
                () -> Assertions.assertEquals(4, movies.size()),
                () -> Assertions.assertEquals("FILM2", movies.get(0).getTitle())
        );
    }

    @Test
    @DisplayName("sort by release date")
    public void test4() {

        List<Movie> movies = sortByMovieValues.sortByReleaseDate(movieList);

        Assertions.assertAll(
                () -> Assertions.assertEquals(4, movies.size()),
                () -> Assertions.assertEquals("FILM2", movies.get(0).getTitle())
        );
    }

    @Test
    @DisplayName("sort by duration")
    public void test5() {

        List<Movie> movies = sortByMovieValues.sortByDuration(movieList);

        Assertions.assertAll(
                () -> Assertions.assertEquals(4, movies.size()),
                () -> Assertions.assertEquals("FILM4", movies.get(0).getTitle())
        );
    }
}
