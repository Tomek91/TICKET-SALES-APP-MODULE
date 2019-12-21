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
import pl.com.app.filersAndSort.SortByCustomerValuesImpl;
import pl.com.app.model.Customer;

import java.util.List;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomerSortTest {

    @InjectMocks
    private SortByCustomerValuesImpl sortByCustomerValues;

    private Random random = new Random();
    private List<Customer> customerList;

    @BeforeEach
    public void init() {
        customerList = List.of(
                Customer.builder().id((new Random().nextInt(100) + 1)).email("test@.pl").name("ADA").surname("KOWAL").loyaltyCardId((new Random().nextInt(100) + 1)).age(11).build(),
                Customer.builder().id((new Random().nextInt(100) + 1)).email("test2@.pl").name("OLA").surname("NOWAK").loyaltyCardId((new Random().nextInt(100) + 1)).age(22).build(),
                Customer.builder().id((new Random().nextInt(100) + 1)).email("test3@.pl").name("WOJTEK").surname("NOWAK3").loyaltyCardId((new Random().nextInt(100) + 1)).age(33).build()
        );

    }

    @Test
    @DisplayName("sort by title")
    public void test1() {

        List<Customer> customers = sortByCustomerValues.sortByAge(customerList);

        Assertions.assertAll(
                () -> Assertions.assertEquals(3, customers.size()),
                () -> Assertions.assertEquals("ADA", customers.get(0).getName())
        );
    }

    @Test
    @DisplayName("sort by surname")
    public void test2() {


        List<Customer> customers = sortByCustomerValues.sortBySurname(customerList);

        Assertions.assertAll(
                () -> Assertions.assertEquals(3, customers.size()),
                () -> Assertions.assertEquals("ADA", customers.get(0).getName())
        );
    }

    @Test
    @DisplayName("sort by name")
    public void test3() {

        List<Customer> customers = sortByCustomerValues.sortByName(customerList);

        Assertions.assertAll(
                () -> Assertions.assertEquals(3, customers.size()),
                () -> Assertions.assertEquals("ADA", customers.get(0).getName())
        );
    }

    @Test
    @DisplayName("sort by release email")
    public void test4() {

        List<Customer> customers = sortByCustomerValues.sortByEmail(customerList);

        Assertions.assertAll(
                () -> Assertions.assertEquals(3, customers.size()),
                () -> Assertions.assertEquals("OLA", customers.get(0).getName())
        );
    }


}
