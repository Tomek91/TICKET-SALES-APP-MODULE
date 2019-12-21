package pl.com.app.service.customer;

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
import pl.com.app.exceptions.MyException;
import pl.com.app.model.Customer;
import pl.com.app.model.Movie;
import pl.com.app.model.SalesStand;
import pl.com.app.repository.CustomerRepository;
import pl.com.app.repository.MovieRepository;
import pl.com.app.repository.SalesStandRepository;
import pl.com.app.service.CustomerServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FindByGenreAndTicketTest {
    @Mock
    private SalesStandRepository salesStandRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerServiceImpl customerService;


    @Test
    @DisplayName("when find by genre and more than three tickets - ticket and genre")
    public void test1() {

        Random random = new Random();

        List<SalesStand> salesStandList = List.of(
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(1).customerId(2).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(1).customerId(2).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(1).customerId(2).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(3).customerId(4).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(5).customerId(6).build()
        );

        Movie movie = Movie.builder().id(random.nextInt(100)).duration(2).genre("horror").title("FILM").releaseDate(LocalDate.now()).price(BigDecimal.TEN).build();

        Customer customer = Customer.builder().id(random.nextInt(100)).age(33).email("test@.pl").surname("NOWAK").name("OLA").loyaltyCardId(random.nextInt(100)).build();

        Mockito
                .when(salesStandRepository.findAll())
                .thenReturn(salesStandList);

        Mockito
                .when(movieRepository.findOneById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(movie));

        Mockito
                .when(customerRepository.findOneById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(customer));

        List<Customer> customers = customerService.findByGenreAndMoreThanThreeTickets("horror");

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, customers.size()),
                () -> Assertions.assertEquals("OLA", customers.get(0).getName())
        );
    }

    @Test
    @DisplayName("when find by genre and more than three tickets - genre but not enough ticket")
    public void test2() {

        Random random = new Random();

        List<SalesStand> salesStandList = List.of(
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(1).customerId(2).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(1).customerId(2).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(3).customerId(4).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(5).customerId(6).build()
        );

        Movie movie = Movie.builder().id(random.nextInt(100)).duration(2).genre("horror").title("FILM").releaseDate(LocalDate.now()).price(BigDecimal.TEN).build();

        Customer customer = Customer.builder().id(random.nextInt(100)).age(33).email("test@.pl").surname("NOWAK").name("OLA").loyaltyCardId(random.nextInt(100)).build();

        Mockito
                .when(salesStandRepository.findAll())
                .thenReturn(salesStandList);

        Mockito
                .when(movieRepository.findOneById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(movie));

        Mockito
                .when(customerRepository.findOneById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(customer));

        List<Customer> customers = customerService.findByGenreAndMoreThanThreeTickets("horror");

        Assertions.assertAll(
                () -> Assertions.assertEquals(0, customers.size())
        );
    }

    @Test
    @DisplayName("when find by genre and more than three tickets - enough ticket but no genre")
    public void test3() {

        Random random = new Random();

        List<SalesStand> salesStandList = List.of(
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(1).customerId(2).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(1).customerId(2).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(3).customerId(4).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(5).customerId(6).build()
        );

        Movie movie = Movie.builder().id(random.nextInt(100)).duration(2).genre("horror").title("FILM").releaseDate(LocalDate.now()).price(BigDecimal.TEN).build();

        Customer customer = Customer.builder().id(random.nextInt(100)).age(33).email("test@.pl").surname("NOWAK").name("OLA").loyaltyCardId(random.nextInt(100)).build();

        Mockito
                .when(salesStandRepository.findAll())
                .thenReturn(salesStandList);

        Mockito
                .when(movieRepository.findOneById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(movie));

        Mockito
                .when(customerRepository.findOneById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(customer));

        List<Customer> customers = customerService.findByGenreAndMoreThanThreeTickets("comedy");

        Assertions.assertAll(
                () -> Assertions.assertEquals(0, customers.size())
        );
    }

    @Test
    @DisplayName("when find by genre and more than three tickets - no sales stand")
    public void test4() {

        Random random = new Random();

        List<SalesStand> salesStandList = Collections.emptyList();

        Movie movie = Movie.builder().id(random.nextInt(100)).duration(2).genre("horror").title("FILM").releaseDate(LocalDate.now()).price(BigDecimal.TEN).build();

        Customer customer = Customer.builder().id(random.nextInt(100)).age(33).email("test@.pl").surname("NOWAK").name("OLA").loyaltyCardId(random.nextInt(100)).build();

        Mockito
                .when(salesStandRepository.findAll())
                .thenReturn(salesStandList);

        Mockito
                .when(movieRepository.findOneById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(movie));

        Mockito
                .when(customerRepository.findOneById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(customer));

        List<Customer> customers = customerService.findByGenreAndMoreThanThreeTickets("comedy");

        Assertions.assertAll(
                () -> Assertions.assertTrue(customers.isEmpty())
        );
    }

    @Test
    @DisplayName("when find by genre and more than three tickets - argument is null")
    public void test5() {


        var e = Assertions.assertThrows(MyException.class, () -> customerService.findByGenreAndMoreThanThreeTickets(null));
        Assertions.assertEquals("CUSTOMER SERVICE, GET CUSTOMERS BY MOVIE GENRE WITH MORE THAN THREE TICKETS", e.getExceptionMessage());
    }
}

