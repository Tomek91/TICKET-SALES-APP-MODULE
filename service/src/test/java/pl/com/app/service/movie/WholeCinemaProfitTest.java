package pl.com.app.service.movie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import pl.com.app.model.Customer;
import pl.com.app.model.LoyaltyCard;
import pl.com.app.model.Movie;
import pl.com.app.model.SalesStand;
import pl.com.app.repository.CustomerRepository;
import pl.com.app.repository.LoyaltyCardRepository;
import pl.com.app.repository.MovieRepository;
import pl.com.app.repository.SalesStandRepository;
import pl.com.app.service.MovieServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WholeCinemaProfitTest {
    @Mock
    private SalesStandRepository salesStandRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private LoyaltyCardRepository loyaltyCardRepository;
    @InjectMocks
    private MovieServiceImpl movieService;

    private Random random = new Random();


    @Test
    @DisplayName("whole cinema profit")
    public void test1() {

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
                        return  Optional.of(Movie.builder().id(3).duration(2).genre("komedia").title("FILM2").releaseDate(LocalDate.now()).price(new BigDecimal(20)).build());
                    } else if(x.getArgument(0).equals(5)){
                        return  Optional.of(Movie.builder().id(5).duration(2).genre("thriller").title("FILM3").releaseDate(LocalDate.now().minusMonths(1L)).price(new BigDecimal(5)).build());
                    } else {
                        return Optional.empty();
                    }
                });

        LoyaltyCard loyaltyCard = LoyaltyCard.builder().id(random.nextInt(100)).expirationDate(LocalDate.now()).discount(BigDecimal.ONE).moviesNumber(3).build();

        Customer customer = Customer.builder().id(random.nextInt(100)).age(33).email("test@.pl").surname("NOWAK").name("OLA").loyaltyCardId(random.nextInt(100)).build();


        Mockito
                .when(movieRepository.findOneById(ArgumentMatchers.anyInt()))
                .thenAnswer(x -> {
                    if (x.getArgument(0).equals(1)){
                        return  Optional.of(Movie.builder().id(1).duration(2).genre("horror").title("FILM1").releaseDate(LocalDate.now().plusMonths(1L)).price(BigDecimal.TEN).build());
                    } else if (x.getArgument(0).equals(3)){
                        return  Optional.of(Movie.builder().id(3).duration(2).genre("komedia").title("FILM2").releaseDate(LocalDate.now()).price(new BigDecimal(20)).build());
                    } else if(x.getArgument(0).equals(5)){
                        return  Optional.of(Movie.builder().id(5).duration(2).genre("thriller").title("FILM3").releaseDate(LocalDate.now().minusMonths(1L)).price(new BigDecimal(5)).build());
                    } else {
                        return Optional.empty();
                    }
                });

        Mockito
                .when(customerRepository.findOneById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(customer));

        Mockito
                .when(loyaltyCardRepository.findOneById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(loyaltyCard));

        Double wholeCinemaProfit = movieService.wholeCinemaProfit();


        Assertions.assertAll(
                () -> Assertions.assertNotNull(wholeCinemaProfit),
                () -> Assertions.assertTrue(wholeCinemaProfit > 0)
        );
    }

    @Test
    @DisplayName("when find most profit movie - no sales")
    public void test2() {

        Mockito
                .when(salesStandRepository.findAll())
                .thenReturn(Collections.emptyList());

        Mockito
                .when(customerRepository.findOneById(ArgumentMatchers.anyInt()))
                .thenAnswer(x -> {
                    if (x.getArgument(0).equals(2)){
                        return  Optional.of( Customer.builder().id(2).age(33).email("test@.pl").surname("NOWAK1").name("OLA1").loyaltyCardId(21).build());
                    } else if (x.getArgument(0).equals(4)){
                        return  Optional.of( Customer.builder().id(4).age(33).email("test@.pl").surname("NOWAK2").name("OLA2").loyaltyCardId(22).build());
                    } else if(x.getArgument(0).equals(6)){
                        return  Optional.of( Customer.builder().id(6).age(33).email("test@.pl").surname("NOWAK3").name("OLA3").loyaltyCardId(23).build());
                    } else {
                        return Optional.empty();
                    }
                });

        Mockito
                .when(loyaltyCardRepository.findOneById(ArgumentMatchers.anyInt()))
                .thenAnswer(x -> {
                    if (x.getArgument(0).equals(21)){
                        return  Optional.of(LoyaltyCard.builder().id(21).expirationDate(LocalDate.now().minusWeeks(1L)).discount(BigDecimal.ONE).moviesNumber(3).build());
                    } else if (x.getArgument(0).equals(22)){
                        return  Optional.of(LoyaltyCard.builder().id(22).expirationDate(LocalDate.now().plusMonths(33L)).discount(BigDecimal.TEN).moviesNumber(3).build());
                    } else if(x.getArgument(0).equals(23)){
                        return  Optional.of(LoyaltyCard.builder().id(23).expirationDate(LocalDate.now().minusWeeks(2L)).discount(new BigDecimal(11)).moviesNumber(3).build());
                    } else {
                        return Optional.empty();
                    }
                });

        Double wholeCinemaProfit = movieService.wholeCinemaProfit();

        Assertions.assertAll(
                () -> Assertions.assertNotNull(wholeCinemaProfit),
                () -> Assertions.assertEquals(0.0, wholeCinemaProfit)
        );
    }

}

