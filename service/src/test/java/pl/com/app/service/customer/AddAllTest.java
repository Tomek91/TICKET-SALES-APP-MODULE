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
import pl.com.app.repository.CustomerRepository;
import pl.com.app.service.CustomerServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AddAllTest {
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    @DisplayName("when add all customers number of invocations")
    public void test1() {
        List<Customer> customers = Arrays.asList(Customer.builder().id((new Random().nextInt(100) + 1)).email("test@.pl").name("ADA").surname("KOWAL").loyaltyCardId((new Random().nextInt(100) + 1)).build(),
                Customer.builder().id((new Random().nextInt(100) + 1)).email("test2@.pl").name("OLA").surname("NOWAK").loyaltyCardId((new Random().nextInt(100) + 1)).build());

        Mockito
                .doNothing()
                .when(customerRepository)
                .addAll(ArgumentMatchers.anyList());

        customerService.addAll(customers);

        Mockito.verify(customerRepository, Mockito.times(1)).addAll(customers);
    }

    @Test
    @DisplayName("when add all customers null argument")
    public void test2() {

//        var e = Assertions.assertThrows(MyException.class, () -> customerService.addAll(null));
//        Assertions.assertEquals("CUSTOMER SERVICE, ADD ALL", e.getExceptionMessage());

        Mockito
                .doThrow(new MyException("CUSTOMER REPOSITORY, ADD"))
                .when(customerRepository).addAll(ArgumentMatchers.any());


        var e = Assertions.assertThrows(MyException.class, () -> customerService.addAll(null));
    }


}
