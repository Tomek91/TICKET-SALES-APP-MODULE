package pl.com.app.service.salesStand;

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
import pl.com.app.service.SalesStandServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class NumberOfMovieForCustomerTest {
    @Mock
    private SalesStandRepository salesStandRepository;
    @InjectMocks
    private SalesStandServiceImpl salesStandService;


    @Test
    @DisplayName("number of movie for customer")
    public void test1() {

        Random random = new Random();

        List<SalesStand> salesStandList = List.of(
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(1).customerId(2).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(1).customerId(2).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(1).customerId(2).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(3).customerId(4).build(),
                SalesStand.builder().id(random.nextInt(100)).startDateTime(LocalDateTime.now()).movieId(5).customerId(6).build()
        );

        Mockito
                .when(salesStandRepository.findAll())
                .thenReturn(salesStandList);

        Integer numberOfMoviesForCustomers = salesStandService.numberOfMoviesForCustomers(4);

        Assertions.assertEquals(1, numberOfMoviesForCustomers);
    }

    @Test
    @DisplayName("number of movie for customer - argument is null")
    public void test5() {


        var e = Assertions.assertThrows(MyException.class, () -> salesStandService.numberOfMoviesForCustomers(null));
        Assertions.assertEquals("SALES STAND SERVICE, NUMBER OF SALES STAND FOR CUSTOMERS", e.getExceptionMessage());
    }
}

