package pl.com.app.service.customer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.Customer;
import pl.com.app.repository.CustomerRepository;
import pl.com.app.service.CustomerServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FindByNameSurnameAndEmailTest {
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerServiceImpl customerService;


    @Test
    @DisplayName("when find by name, surname and email")
    public void test1() {

        Random random = new Random();

        Customer customer = Customer.builder().id(random.nextInt(100)).age(33).email("test@.pl").surname("NOWAK").name("OLA").loyaltyCardId(random.nextInt(100)).build();

        Mockito
                .when(customerRepository.findByNameSurnameAndEmail("OLA", "NOWAK", "test@.pl"))
                .thenReturn(Optional.of(customer));


        Optional<Customer> customerOpt = customerService.findByNameSurnameAndEmail("OLA", "NOWAK", "test@.pl");

        Assertions.assertEquals(Optional.of(customer), customerOpt);
    }

    @Test
    @DisplayName("when find by name, surname and email - not exist")
    public void test2() {
        Mockito
                .when(customerRepository.findByNameSurnameAndEmail("OLA", "NOWAK", "test@.pl"))
                .thenReturn(Optional.empty());


        Optional<Customer> customerOpt = customerService.findByNameSurnameAndEmail("OLA", "NOWAK", "test@.pl");

        Assertions.assertTrue(customerOpt.isEmpty());
    }

    @Test
    @DisplayName("when find by movie - name is null")
    public void test5() {
        var e = Assertions.assertThrows(MyException.class, () -> customerService.findByNameSurnameAndEmail("OLA", null, "test@.pl"));
        Assertions.assertEquals("CUSTOMER SERVICE, FIND BY NAME SURNAME AND EMAIL", e.getExceptionMessage());
    }

    @Test
    @DisplayName("when find by movie - surname is null")
    public void test6() {
        var e = Assertions.assertThrows(MyException.class, () -> customerService.findByNameSurnameAndEmail("OLA", null, "test@.pl"));
        Assertions.assertEquals("CUSTOMER SERVICE, FIND BY NAME SURNAME AND EMAIL", e.getExceptionMessage());
    }

    @Test
    @DisplayName("when find by movie - mail is null")
    public void test7() {
        var e = Assertions.assertThrows(MyException.class, () -> customerService.findByNameSurnameAndEmail("OLA", "NOWAK", null));
        Assertions.assertEquals("CUSTOMER SERVICE, FIND BY NAME SURNAME AND EMAIL", e.getExceptionMessage());
    }
}

