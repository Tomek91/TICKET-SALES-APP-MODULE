package pl.com.app.service;


import pl.com.app.exceptions.MyException;
import pl.com.app.model.Customer;
import pl.com.app.model.Movie;
import pl.com.app.model.SalesStand;
import pl.com.app.repository.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private SalesStandRepository salesStandRepository = new SalesStandRepositoryImpl();
    private MovieRepository movieRepository = new MovieRepositoryImpl();
    private LoyaltyCardRepository loyaltyCardRepository = new LoyaltyCardRepositoryImpl();


    @Override
    public void addAll(List<Customer> items) {
        try {
            if (items == null) {
                throw new NullPointerException("CUSTOMERS IS NULL");
            }
            customerRepository.addAll(items);

        } catch (Exception e) {
            throw new MyException("CUSTOMER SERVICE, ADD ALL");
        }
    }

    @Override
    public void deleteAll() {
        try {
            customerRepository.deleteAll();

        } catch (Exception e) {
            throw new MyException("CUSTOMER SERVICE, DELETE ALL");
        }
    }

    @Override
    public void deleteOne(Integer id) {
        try {
            if (id == null){
                throw new NullPointerException("ID IS NULL");
            }
            customerRepository.delete(id);
        } catch (Exception e) {
            throw new MyException("CUSTOMER SERVICE, DELETE ONE");
        }
    }

    @Override
    public void updateOne(Customer customer) {
        try {
            if (customer == null){
                throw new NullPointerException("CUSTOMER IS NULL");
            }
            customerRepository.update(customer);
        } catch (Exception e) {
            throw new MyException("CUSTOMER SERVICE, UPDATE ONE");
        }
    }

    @Override
    public void updateAll(String columnName, Object columnValue) {
        try {
            if (columnName == null || columnValue == null) {
                throw new NullPointerException("CONDITION IS NULL");
            }
            customerRepository.updateAll(columnName, columnValue);
        } catch (Exception e) {
            throw new MyException("CUSTOMER SERVICE, UPDATE ALL");
        }
    }

    @Override
    public Optional<Customer> findById(Integer id) {
       Optional<Customer> customerOpt = Optional.empty();
        try {
            if (id == null) {
                throw new NullPointerException("ID IS NULL");
            }
            customerOpt = customerRepository.findOneById(id);

        } catch (Exception e) {
            throw new MyException("CUSTOMER SERVICE, FIND BY ID");
        }
        return customerOpt;
    }

    @Override
    public Optional<Customer> findByNameSurnameAndEmail(String name, String surname, String email) {
        Optional<Customer> customerOpt = Optional.empty();
        try {
            if (name == null || surname == null || email == null) {
                throw new NullPointerException("CONDITIONS IS NULL");
            }
            customerOpt = customerRepository.findByNameSurnameAndEmail(name, surname, email);

        } catch (Exception e) {
            throw new MyException("CUSTOMER SERVICE, FIND BY NAME SURNAME AND EMAIL");
        }
        return customerOpt;
    }

    @Override
    public List<Customer> findByCustom(String columnName, Object columnValue) {
        List<Customer> customers = new ArrayList<>();
        try {
            if (columnName == null || columnValue == null) {
                throw new NullPointerException("CONDITION IS NULL");
            }
            customers = customerRepository.findByCustom(columnName, columnValue);

        } catch (Exception e) {
            throw new MyException("CUSTOMER SERVICE, FIND BY CUSTOM");
        }
        return customers;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try {
            customers = customerRepository.findAll();

        } catch (Exception e) {
            throw new MyException("CUSTOMER SERVICE, FIND ALL");
        }
        return customers;
    }

    @Override
    public void addCustomer(Customer customer) {
        try {
            if (customer == null) {
                throw new NullPointerException("CUSTOMER IS NULL");
            }
            customerRepository.add(customer);

        } catch (Exception e) {
            throw new MyException("CUSTOMER SERVICE, ADD CUSTOMER");
        }
    }

    @Override
    public List<Customer> findByGenreAndMoreThanThreeTickets(final String genre) {
        try {
            if (genre == null) {
                throw new NullPointerException("GENRE IS NULL");
            }

            Map<Integer, List<SalesStand>> groupedByCustomerId = salesStandRepository
                    .findAll()
                    .stream()
                    .collect(Collectors.groupingBy(SalesStand::getCustomerId));

            return groupedByCustomerId
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> e.getValue()
                                    .stream()
                                    .map(x -> movieRepository.findOneById(x.getMovieId()).orElse(null))
                                    .filter(Objects::nonNull)
                                    .filter(x -> x.getGenre().equals(genre))
                                    .count()
                    ))
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue() >= 3)
                    .map(e -> customerRepository.findOneById(e.getKey()).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("CUSTOMER SERVICE, GET CUSTOMERS BY MOVIE GENRE WITH MORE THAN THREE TICKETS");
        }
    }

    @Override
    public List<Customer> customersCardExpiredAfterDate(LocalDate date) {
        List<Customer> customers = new ArrayList<>();
        try {
            if (date == null) {
                throw new NullPointerException("DATE IS NULL");
            }

            customers = loyaltyCardRepository.findAll()
                    .stream()
                    .filter(x -> x.getExpirationDate().isAfter(date))
                    .map(x -> customerRepository.findOneByLoyaltyCardId( x.getId()).orElseThrow(NullPointerException::new))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MyException("CUSTOMER SERVICE, CARD EXPIRED AFTER DATE");
        }
        return customers;
    }

    @Override
    public List<Customer> findCustomersByMovie(String title) {
        List<Customer> customers = new ArrayList<>();
        try {
            if (title == null) {
                throw new NullPointerException("MOVIE TITLE IS NULL");
            }

            if (!doesMovieWithTitleExist(title)) {
                throw new IllegalArgumentException("MOVIE WITH TITLE " + title + " DOESN'T EXIST!");
            }

            customers = salesStandRepository.findAll()
                    .stream()
                    .filter(x -> {
                        Movie movie = movieRepository
                                .findOneById(x.getMovieId())
                                .orElseThrow(NullPointerException::new);
                        return title.equals(movie.getTitle());
                    })
                    .map(x -> customerRepository
                                .findOneById(x.getCustomerId())
                                .orElseThrow(NullPointerException::new)
                    )
                    .distinct()
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new MyException("CUSTOMER SERVICE, FIND BY MOVIE TITLE");
        }
        return customers;
    }

    private boolean doesMovieWithTitleExist(String title) {
        return !movieRepository.findByCustom("title", title).isEmpty();
    }
}
