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
import pl.com.app.filersAndSort.FilterByImpl;
import pl.com.app.filersAndSort.SortByCustomerValuesImpl;
import pl.com.app.model.Customer;
import pl.com.app.model.Movie;
import pl.com.app.model.MovieSalesStand;
import pl.com.app.model.SalesStand;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FilterByTest {

    @InjectMocks
    private FilterByImpl filterBy;

    private Random random = new Random();
    private List<MovieSalesStand> movieSalesStands;

    @BeforeEach
    public void init() {
        movieSalesStands = List.of(
                MovieSalesStand.builder().movie(Movie.builder().id(random.nextInt(100)).duration(2).genre("horror1").title("FILM1").releaseDate(LocalDate.now().minusDays(1)).price(BigDecimal.TEN).build()).salesStand(SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(5).customerId(6).build()).build(),
                MovieSalesStand.builder().movie(Movie.builder().id(random.nextInt(100)).duration(1).genre("horror4").title("FILM4").releaseDate(LocalDate.now().minusDays(1)).price(BigDecimal.TEN).build()).salesStand(SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(3).customerId(4).build()).build(),
                MovieSalesStand.builder().movie(Movie.builder().id(random.nextInt(100)).duration(4).genre("horror3").title("FILM3").releaseDate(LocalDate.now().minusDays(2)).price(BigDecimal.ONE).build()).salesStand(SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(1).customerId(2).build()).build()
        );
    }

    @Test
    @DisplayName("filter by dates")
    public void test1() {

        List<MovieSalesStand> movieSalesStands = filterBy.filterByDates(LocalDate.now().minusDays(11), LocalDate.now().plusDays(2), this.movieSalesStands);

        Assertions.assertAll(
                () -> Assertions.assertEquals(3, movieSalesStands.size()),
                () -> Assertions.assertEquals("FILM1", movieSalesStands.get(0).getMovie().getTitle())
        );
    }

    @Test
    @DisplayName("filter by duration")
    public void test2() {
        List<MovieSalesStand> movieSalesStands = filterBy.filterByDuration(2, this.movieSalesStands);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, movieSalesStands.size()),
                () -> Assertions.assertEquals("FILM1", movieSalesStands.get(0).getMovie().getTitle())
        );
    }

    @Test
    @DisplayName("filter by duration")
    public void test3() {
        List<MovieSalesStand> movieSalesStands = filterBy.filterByGenre("horror4", this.movieSalesStands);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, movieSalesStands.size()),
                () -> Assertions.assertEquals("FILM4", movieSalesStands.get(0).getMovie().getTitle())
        );
    }

    @Test
    @DisplayName("filter by all")
    public void test4() {
        List<MovieSalesStand> movieSalesStands = filterBy.filterByAll("horror4", LocalDate.now().minusDays(11), LocalDate.now(), 2, this.movieSalesStands);

        Assertions.assertAll(
                () -> Assertions.assertEquals(0, movieSalesStands.size())
        );
    }


}
