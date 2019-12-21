package pl.com.app.service;


import pl.com.app.exceptions.MyException;
import pl.com.app.model.Customer;
import pl.com.app.model.LoyaltyCard;
import pl.com.app.model.Movie;
import pl.com.app.model.SalesStand;
import pl.com.app.repository.*;
import pl.com.app.service.manage.MovieConstant;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class MovieServiceImpl implements MovieService {
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private SalesStandRepository salesStandRepository = new SalesStandRepositoryImpl();
    private MovieRepository movieRepository = new MovieRepositoryImpl();
    private LoyaltyCardRepository loyaltyCardRepository = new LoyaltyCardRepositoryImpl();

    @Override
    public void addAll(List<Movie> items) {
        try {
            if (items == null) {
                throw new NullPointerException("MOVIES IS NULL");
            }
            movieRepository.addAll(items);

        } catch (Exception e) {
            throw new MyException("MOVIE SERVICE, ADD ALL");
        }
    }

    @Override
    public void deleteAll() {
        try {
            movieRepository.deleteAll();

        } catch (Exception e) {
            throw new MyException("MOVIE SERVICE, DELETE ALL");
        }
    }

    @Override
    public void deleteOne(Integer id) {
        try {
            if (id == null){
                throw new NullPointerException("ID IS NULL");
            }
            movieRepository.delete(id);
        } catch (Exception e) {
            throw new MyException("MOVIE SERVICE, DELETE ONE");
        }
    }

    @Override
    public void updateOne(Movie movie) {
        try {
            if (movie == null){
                throw new NullPointerException("MOVIE IS NULL");
            }
            movieRepository.update(movie);
        } catch (Exception e) {
            throw new MyException("MOVIE SERVICE, UPDATE ONE");
        }
    }

    @Override
    public void updateAll(String columnName, Object columnValue) {
        try {
            if (columnName == null || columnValue == null) {
                throw new NullPointerException("CONDITION IS NULL");
            }
            movieRepository.updateAll(columnName, columnValue);
        } catch (Exception e) {
            throw new MyException("MOVIE SERVICE, UPDATE ALL");
        }
    }

    @Override
    public Optional<Movie> findById(Integer id) {
        Optional<Movie> movieOpt = Optional.empty();
        try {
            if (id == null) {
                throw new NullPointerException("ID IS NULL");
            }
            movieOpt = movieRepository.findOneById(id);

        } catch (Exception e) {
            throw new MyException("MOVIE SERVICE, FIND BY ID");
        }
        return movieOpt;
    }

    @Override
    public List<Movie> findByCustom(String columnName, Object columnValue) {
        List<Movie> movies = new ArrayList<>();
        try {
            if (columnName == null || columnValue == null) {
                throw new NullPointerException("CONDITION IS NULL");
            }
            movies = movieRepository.findByCustom(columnName, columnValue);

        } catch (Exception e) {
            throw new MyException("MOVIE SERVICE, FIND BY CUSTOM");
        }
        return movies;
    }

    @Override
    public List<Movie> findAll() {
        List<Movie> movies = new ArrayList<>();
        try {
            movies = movieRepository.findAll();

        } catch (Exception e) {
            throw new MyException("MOVIE SERVICE, FIND ALL");
        }
        return movies;
    }

    @Override
    public void addMovie(Movie movie) {
        try {
            if (movie == null) {
                throw new NullPointerException("MOVIE IS NULL");
            }
            movieRepository.add(movie);

        } catch (Exception e) {
            throw new MyException("MOVIE SERVICE, ADD MOVIE");
        }
    }

    private Map<Movie, Double> movieProfitMap(){
        Map<Movie, List<SalesStand>> groupedByMovie = salesStandRepository
                .findAll()
                .stream()
                .collect(Collectors.groupingBy(SalesStand::getMovieId))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> movieRepository
                                .findOneById(e.getKey())
                                .orElseThrow(NullPointerException::new),
                        Map.Entry::getValue
                        )
                );

        return groupedByMovie
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .map(x -> {
                                    Customer customer = customerRepository
                                            .findOneById(x.getCustomerId())
                                            .orElseThrow(NullPointerException::new);
                                    if (customer.getLoyaltyCardId() != null && customer.getLoyaltyCardId() > 0){
                                        LoyaltyCard loyaltyCard = loyaltyCardRepository
                                                .findOneById(customer.getLoyaltyCardId())
                                                .orElseThrow(NullPointerException::new);

                                        if (isActiveLoyaltyCard(customer.getId(), x, loyaltyCard)){
                                            return e.getKey().getPrice()
                                                    .multiply(new BigDecimal(100).subtract(loyaltyCard.getDiscount()
                                                            .divide(new BigDecimal(100),  2, RoundingMode.FLOOR))
                                                    );
                                        }
                                    }
                                    return e.getKey().getPrice();
                                })
                                .mapToDouble(BigDecimal::doubleValue)
                                .sum()
                ));
    }

    private boolean isActiveLoyaltyCard(Integer customerId, SalesStand salesStand, LoyaltyCard loyaltyCard) {
        return salesStand.getStartDateTime().toLocalDate().isBefore(loyaltyCard.getExpirationDate())
                && isCustomerHaveFreeMovieNumbers(customerId, salesStand);
    }

    private boolean isCustomerHaveFreeMovieNumbers(Integer customerId, SalesStand salesStand) {
        List<SalesStand> salesStandWithDiscount = salesStandRepository.findAll()
                .stream()
                .filter(x -> x.getCustomerId() == customerId)
                .sorted(Comparator.comparing(SalesStand::getStartDateTime))
                .skip(MovieConstant.NUMBER_OF_MOVIES_TO_LOYALTY_CARD)
                .collect(Collectors.toList());

        if (salesStandWithDiscount.size() > MovieConstant.NUMBER_OF_MOVIES_WITH_DISCOUNT){
            salesStandWithDiscount = salesStandWithDiscount.subList(0, MovieConstant.NUMBER_OF_MOVIES_WITH_DISCOUNT);
        }

        return salesStandWithDiscount.contains(salesStand);
    }

    @Override
    public Optional<Movie> theMostProfitMovie() {
        Optional<Movie> movieOpt = Optional.empty();
        try {
            movieOpt = movieProfitMap()
                    .entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey);
        } catch (Exception e) {
            throw new MyException("MOVIE SERVICE, THE MOST PROFIT MOVIE");
        }
        return movieOpt;
    }

    @Override
    public Double wholeCinemaProfit() {
        Double cinemaProfit = 0.0;
        try {
            cinemaProfit = movieProfitMap()
                    .values()
                    .stream()
                    .filter(Objects::nonNull)
                    .mapToDouble(Double::doubleValue)
                    .sum();
        } catch (Exception e) {
            throw new MyException("MOVIE SERVICE, WHOLE CINEMA PROFT");
        }
        return BigDecimal.valueOf(cinemaProfit)
                .setScale(2, RoundingMode.FLOOR)
                .doubleValue();
    }

    private List<Movie> moviesWatchMoreThanThree() {
        Map<Integer, List<SalesStand>> groupedByMovieId = salesStandRepository
                .findAll()
                .stream()
                .collect(Collectors.groupingBy(SalesStand::getMovieId));

        return groupedByMovieId.entrySet()
                .stream()
                .filter(e -> e.getValue().size() > 3)
                .map(e -> movieRepository.findOneById(e.getKey()).orElseThrow(NullPointerException::new))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Movie> theNewestMovieWatchMoreThanThree() {
        Optional<Movie> movieOpt = Optional.empty();
        try {
            movieOpt = moviesWatchMoreThanThree()
                    .stream()
                    .max(Comparator.comparing(Movie::getReleaseDate));
        } catch (Exception e) {
            throw new MyException("MOVIE SERVICE, THE NEWEST MOVIE WATCH MORE THAN THREE TIMES");
        }
        return movieOpt;

    }

    @Override
    public Optional<Movie> theOldestMovieWatchMoreThanThree() {
        Optional<Movie> movieOpt = Optional.empty();
        try {
            movieOpt = moviesWatchMoreThanThree()
                    .stream()
                    .min(Comparator.comparing(Movie::getReleaseDate));
        } catch (Exception e) {
            throw new MyException("MOVIE SERVICE, THE OLDEST MOVIE WATCH MORE THAN THREE TIMES");
        }
        return movieOpt;
    }
}
