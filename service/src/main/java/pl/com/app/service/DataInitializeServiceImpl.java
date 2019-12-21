package pl.com.app.service;

import pl.com.app.connection.DbConnection;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.Customer;
import pl.com.app.model.LoyaltyCard;
import pl.com.app.model.Movie;
import pl.com.app.model.SalesStand;
import pl.com.app.parsers.implementations.CustomerParser;
import pl.com.app.parsers.implementations.LoyaltyParser;
import pl.com.app.parsers.implementations.MoviesParser;
import pl.com.app.parsers.implementations.SalesStandParser;
import pl.com.app.parsers.interfaces.FileNames;
import pl.com.app.parsers.interfaces.Parser;

import java.util.List;

public class DataInitializeServiceImpl implements DataInitializeService {

    private CustomerService customerService = new CustomerServiceImpl();
    private LoyaltyCardService loyaltyCardService = new LoyaltyCardServiceImpl();
    private SalesStandService salesStandService = new SalesStandServiceImpl();
    private MovieService movieService = new MovieServiceImpl();
    private SequenceService sequenceService = new SequenceServiceImpl();

    @Override
    public void initialize() {
        try {
            DbConnection.connection().setAutoCommit(false);

            deleteAll();
            List<Customer> customers = Parser.parseFile(FileNames.CUSTOMERS, new CustomerParser());
            List<LoyaltyCard> loyaltyCards = Parser.parseFile(FileNames.LOYALTY_CARDS, new LoyaltyParser());
            List<Movie> movies = Parser.parseFile(FileNames.MOVIES, new MoviesParser());
            List<SalesStand> salesStand = Parser.parseFile(FileNames.SALES_STAND, new SalesStandParser());

            customerService.addAll(customers);
            loyaltyCardService.addAll(loyaltyCards);
            movieService.addAll(movies);
            salesStandService.addAll(salesStand);

            DbConnection.connection().commit();
            DbConnection.connection().setAutoCommit(true);
        } catch (Exception e) {
            try {
                DbConnection.connection().rollback();
            } catch (Exception ee) {
                throw new MyException("DATA INITIALIZATION ROLLBACK EXCEPTION");
            }
            throw new MyException("DATA INITIALIZATION EXCEPTION");
        }
    }

    private void deleteAll() {
        customerService.deleteAll();
        loyaltyCardService.deleteAll();
        salesStandService.deleteAll();
        movieService.deleteAll();
        sequenceService.deleteAll();
    }
}
