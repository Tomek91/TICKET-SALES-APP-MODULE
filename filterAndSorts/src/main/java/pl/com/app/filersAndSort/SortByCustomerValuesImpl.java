package pl.com.app.filersAndSort;

import pl.com.app.exceptions.MyException;
import pl.com.app.model.Customer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortByCustomerValuesImpl implements SortByCustomerValues<Customer>{

    @Override
    public List<Customer> sortByAge(List<Customer> customers) {
        List<Customer> customerList = new ArrayList<>();
        try {
            if (customers == null) {
                throw new NullPointerException("CUSTOMERS IS NULL");
            }
            customerList = customers.stream()
                    .sorted(Comparator.comparing(Customer::getAge))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new MyException("CUSTOMER, SORT BY AGE");
        }
        return customerList;
    }

    @Override
    public List<Customer> sortByEmail(List<Customer> customers) {
        List<Customer> customerList = new ArrayList<>();
        try {
            if (customers == null) {
                throw new NullPointerException("CUSTOMERS IS NULL");
            }
            customerList = customers.stream()
                    .sorted(Comparator.comparing(Customer::getEmail))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new MyException("CUSTOMER, SORT BY EMAIL");
        }
        return customerList;
    }

    @Override
    public List<Customer> sortBySurname(List<Customer> customers) {
        List<Customer> customerList = new ArrayList<>();
        try {
            if (customers == null) {
                throw new NullPointerException("CUSTOMERS IS NULL");
            }
            customerList = customers.stream()
                    .sorted(Comparator.comparing(Customer::getSurname))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new MyException("CUSTOMER, SORT BY SURNAME");
        }
        return customerList;
    }

    @Override
    public List<Customer> sortByName(List<Customer> customers) {
        List<Customer> customerList = new ArrayList<>();
        try {
            if (customers == null) {
                throw new NullPointerException("CUSTOMERS IS NULL");
            }
            customerList = customers.stream()
                    .sorted(Comparator.comparing(Customer::getName))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new MyException("CUSTOMER, SORT BY NAME");
        }
        return customerList;
    }


}
