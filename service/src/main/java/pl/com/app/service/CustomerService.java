package pl.com.app.service;


import pl.com.app.model.Customer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    void addAll(List<Customer> items);
    void deleteAll();
    void deleteOne(Integer id);
    void updateOne(Customer customer);
    void updateAll(String columnName, Object columnValue);
    Optional<Customer> findById(Integer id);
    Optional<Customer> findByNameSurnameAndEmail(String name, String surname, String email);
    List<Customer> findByCustom(String columnName, Object columnValue);
    List<Customer> findAll();
    void addCustomer(Customer customer);
    List<Customer> findByGenreAndMoreThanThreeTickets(String genre);
    List<Customer> customersCardExpiredAfterDate(LocalDate date);
    List<Customer> findCustomersByMovie(String title);
}
