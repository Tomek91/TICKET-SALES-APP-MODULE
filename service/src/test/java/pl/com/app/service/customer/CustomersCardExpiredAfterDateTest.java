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
import pl.com.app.model.LoyaltyCard;
import pl.com.app.model.Movie;
import pl.com.app.model.SalesStand;
import pl.com.app.repository.CustomerRepository;
import pl.com.app.repository.LoyaltyCardRepository;
import pl.com.app.repository.MovieRepository;
import pl.com.app.repository.SalesStandRepository;
import pl.com.app.service.CustomerServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomersCardExpiredAfterDateTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private LoyaltyCardRepository loyaltyCardRepository;
    @InjectMocks
    private CustomerServiceImpl customerService;


    @Test
    @DisplayName("when find customer card expire after date")
    public void test1() {

        Random random = new Random();

        List<LoyaltyCard> loyaltyCards = List.of(
                LoyaltyCard.builder().id(random.nextInt(100)).expirationDate(LocalDate.now()).moviesNumber(1).discount(BigDecimal.ONE).build(),
                LoyaltyCard.builder().id(random.nextInt(100)).expirationDate(LocalDate.now().plusDays(1L)).moviesNumber(1).discount(BigDecimal.ONE).build(),
                LoyaltyCard.builder().id(random.nextInt(100)).expirationDate(LocalDate.now()).moviesNumber(1).discount(BigDecimal.ONE).build(),
                LoyaltyCard.builder().id(random.nextInt(100)).expirationDate(LocalDate.now().plusDays(2L)).moviesNumber(1).discount(BigDecimal.ONE).build()
        );

        Customer customer = Customer.builder().id(random.nextInt(100)).age(33).email("test@.pl").surname("NOWAK").name("OLA").loyaltyCardId(random.nextInt(100)).build();

        Mockito
                .when(loyaltyCardRepository.findAll())
                .thenReturn(loyaltyCards);

        Mockito
                .when(customerRepository.findOneByLoyaltyCardId(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(customer));

        List<Customer> customers = customerService.customersCardExpiredAfterDate(LocalDate.now());

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, customers.size()),
                () -> Assertions.assertEquals("OLA", customers.get(0).getName())
        );
    }

    @Test
    @DisplayName("when find customer card expire after date - boundary condition")
    public void test2() {

        Random random = new Random();

        List<LoyaltyCard> loyaltyCards = List.of(
                LoyaltyCard.builder().id(random.nextInt(100)).expirationDate(LocalDate.now()).moviesNumber(1).discount(BigDecimal.ONE).build()
        );

        Customer customer = Customer.builder().id(random.nextInt(100)).age(33).email("test@.pl").surname("NOWAK").name("OLA").loyaltyCardId(random.nextInt(100)).build();

        Mockito
                .when(loyaltyCardRepository.findAll())
                .thenReturn(loyaltyCards);

        Mockito
                .when(customerRepository.findOneByLoyaltyCardId(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(customer));

        List<Customer> customers = customerService.customersCardExpiredAfterDate(LocalDate.now());

        Assertions.assertAll(
                () -> Assertions.assertEquals(0, customers.size())
        );
    }

    @Test
    @DisplayName("when find customer card expire after date - argument is null")
    public void test3() {
        var e = Assertions.assertThrows(MyException.class, () -> customerService.customersCardExpiredAfterDate(null));
        Assertions.assertEquals("CUSTOMER SERVICE, CARD EXPIRED AFTER DATE", e.getExceptionMessage());
    }
}

