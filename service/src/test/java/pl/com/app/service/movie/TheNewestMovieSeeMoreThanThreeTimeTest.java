package pl.com.app.service.movie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.com.app.model.Movie;
import pl.com.app.model.SalesStand;
import pl.com.app.repository.MovieRepository;
import pl.com.app.repository.SalesStandRepository;
import pl.com.app.service.MovieServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TheNewestMovieSeeMoreThanThreeTimeTest {
    @Mock
    private SalesStandRepository salesStandRepository;
    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private MovieServiceImpl movieService;


    @Test
    @DisplayName("when find by the oldest movie see more than three times - by date")
    public void test1() {

        Random random = new Random();

        List<SalesStand> salesStandList = List.of(
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(1).customerId(2).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(1).customerId(2).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(1).customerId(2).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(1).customerId(2).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(3).customerId(4).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(3).customerId(4).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(3).customerId(4).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(3).customerId(4).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(3).customerId(4).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(5).customerId(6).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(5).customerId(6).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(5).customerId(6).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(5).customerId(6).build()
        );

        Mockito
                .when(salesStandRepository.findAll())
                .thenReturn(salesStandList);

        Mockito
                .when(movieRepository.findOneById(ArgumentMatchers.anyInt()))
                .thenAnswer(x -> {
                    if (x.getArgument(0).equals(1)){
                        return  Optional.of(Movie.builder().id(1).duration(2).genre("horror").title("FILM1").releaseDate(LocalDate.now().plusMonths(1L)).price(BigDecimal.TEN).build());
                    } else if (x.getArgument(0).equals(3)){
                        return  Optional.of(Movie.builder().id(3).duration(2).genre("komedia").title("FILM2").releaseDate(LocalDate.now()).price(BigDecimal.TEN).build());
                    } else if(x.getArgument(0).equals(5)){
                        return  Optional.of(Movie.builder().id(5).duration(2).genre("thriller").title("FILM3").releaseDate(LocalDate.now().minusMonths(1L)).price(BigDecimal.TEN).build());
                    } else {
                        return Optional.empty();
                    }
                });


        Optional<Movie> theNewestMovieWatchMoreThanThree = movieService.theNewestMovieWatchMoreThanThree();

        Assertions.assertAll(
                () -> Assertions.assertTrue( theNewestMovieWatchMoreThanThree.isPresent()),
                () -> Assertions.assertEquals("FILM1", theNewestMovieWatchMoreThanThree.get().getTitle())
        );
    }
    @Test
    @DisplayName("when find by the oldest movie see more than three times - three times")
    public void test2() {

        Random random = new Random();

        List<SalesStand> salesStandList = List.of(
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(1).customerId(2).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(3).customerId(4).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(3).customerId(4).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(3).customerId(4).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(3).customerId(4).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(3).customerId(4).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(5).customerId(6).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(5).customerId(6).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(5).customerId(6).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(5).customerId(6).build()
        );

        Mockito
                .when(salesStandRepository.findAll())
                .thenReturn(salesStandList);

        Mockito
                .when(movieRepository.findOneById(ArgumentMatchers.anyInt()))
                .thenAnswer(x -> {
                    if (x.getArgument(0).equals(1)){
                        return  Optional.of(Movie.builder().id(1).duration(2).genre("horror").title("FILM1").releaseDate(LocalDate.now().plusMonths(1L)).price(BigDecimal.TEN).build());
                    } else if (x.getArgument(0).equals(3)){
                        return  Optional.of(Movie.builder().id(3).duration(2).genre("komedia").title("FILM2").releaseDate(LocalDate.now()).price(BigDecimal.TEN).build());
                    } else if(x.getArgument(0).equals(5)){
                        return  Optional.of(Movie.builder().id(5).duration(2).genre("thriller").title("FILM3").releaseDate(LocalDate.now().minusMonths(1L)).price(BigDecimal.TEN).build());
                    } else {
                        return Optional.empty();
                    }
                });


        Optional<Movie> theNewestMovieWatchMoreThanThree = movieService.theNewestMovieWatchMoreThanThree();

        Assertions.assertAll(
                () -> Assertions.assertTrue( theNewestMovieWatchMoreThanThree.isPresent()),
                () -> Assertions.assertEquals("FILM2", theNewestMovieWatchMoreThanThree.get().getTitle())
        );
    }
    @Test
    @DisplayName("when find by the oldest movie see more than three times - empty")
    public void test3() {

        Random random = new Random();

        List<SalesStand> salesStandList = List.of(
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(1).customerId(2).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(1).customerId(2).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(3).customerId(4).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(5).customerId(6).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(5).customerId(6).build()
        );

        Mockito
                .when(salesStandRepository.findAll())
                .thenReturn(salesStandList);

        Mockito
                .when(movieRepository.findOneById(ArgumentMatchers.anyInt()))
                .thenAnswer(x -> {
                    if (x.getArgument(0).equals(1)){
                        return  Movie.builder().id(1).duration(2).genre("horror").title("FILM1").releaseDate(LocalDate.now().plusMonths(1L)).price(BigDecimal.TEN).build();
                    } else if (x.getArgument(0).equals(3)){
                        return  Movie.builder().id(3).duration(2).genre("komedia").title("FILM2").releaseDate(LocalDate.now()).price(BigDecimal.TEN).build();
                    } else if(x.getArgument(0).equals(5)){
                        return  Movie.builder().id(5).duration(2).genre("thriller").title("FILM3").releaseDate(LocalDate.now().minusMonths(1L)).price(BigDecimal.TEN).build();
                    } else {
                        return Optional.empty();
                    }
                });


        Optional<Movie> theNewestMovieWatchMoreThanThree = movieService.theNewestMovieWatchMoreThanThree();

        Assertions.assertAll(
                () -> Assertions.assertTrue( theNewestMovieWatchMoreThanThree.isEmpty())
        );
    }

}

