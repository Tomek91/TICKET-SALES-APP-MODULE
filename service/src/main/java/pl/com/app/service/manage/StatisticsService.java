package pl.com.app.service.manage;


import pl.com.app.connection.DataReader;
import pl.com.app.model.Customer;
import pl.com.app.model.Movie;
import pl.com.app.service.CustomerService;
import pl.com.app.service.CustomerServiceImpl;
import pl.com.app.service.MovieService;
import pl.com.app.service.MovieServiceImpl;
import pl.com.app.service.menu.Converts;
import pl.com.app.service.menu.Menu;
import pl.com.app.service.menu.TicketSalesSimulator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public class StatisticsService {
    private CustomerService customerService = new CustomerServiceImpl();
    private MovieService movieService = new MovieServiceImpl();
    private DataReader dataReader = new DataReader();

    public void customersWhoCardExpiredAfterDate() {
        System.out.println("Podaj datę ...");
        LocalDate date = dataReader.getLocalDate();
        System.out.println("Klienci, których karta wygasa po dacie " + date + ":");
        List<Customer> customers = customerService.customersCardExpiredAfterDate(date);
        System.out.println(Converts.getOrAbsence(customers));
    }

    public void theNewestAndOldestMovieWhichWatchMoreThanFourCustomers() {
        System.out.println("Najnowszy film, kóry obejrzało więcej niż 4 osoby:");
        System.out.println(Converts.getOrAbsence(movieService.theNewestMovieWatchMoreThanThree()));
        System.out.println("Najstarszy film, kóry obejrzało więcej niż 4 osoby:");
        System.out.println(Converts.getOrAbsence(movieService.theOldestMovieWatchMoreThanThree()));
    }

    public void wholeCinemaProfit() {
        System.out.println("Łączny przychód kina:");
        System.out.println(movieService.wholeCinemaProfit());
    }

    public void theMostProfitMovie() {
        System.out.println("Film na którym zarobiono najwięcej:");
        Optional<Movie> movie = movieService.theMostProfitMovie();
        System.out.println(Converts.getOrAbsence(movie));
    }

    public void customersWhoBuyHorrorTicetsMoreThanThree() {
        System.out.println("Oto klienci którzy kupili co najmniej 3 bilety na film z gatunku horror:");
        List<Customer> customers = customerService.findByGenreAndMoreThanThreeTickets("horror");
        System.out.println(Converts.getOrAbsence(customers));
    }

    public void customersWhichBuyMovieTicket() {
        System.out.println("Podaj tytuł filmu ...");
        String title = dataReader.getString();
        System.out.println(String.join(" ",
                "Klienci, którzy kupili bilet na film",
                title,
                ":"));
        List<Customer> customers = customerService.findCustomersByMovie(title);
        System.out.println(Converts.getOrAbsence(customers));
    }


    public static void statistics() {
        String data = null;
        do {
            System.out.println(TicketSalesSimulator.PREFIX);
            System.out.println("Statystyki.\nNapisz, co chcesz zobaczyć.");
            Menu statisticsMenu = Menu.builder().menuList(Menu.createStatisticsMenu()).build();
            System.out.println(Menu.showMenu(statisticsMenu.getMenuList()));
            data = new DataReader().getString();
            System.out.println(TicketSalesSimulator.PREFIX);
            retrieveStatisticsData(data);
        } while (!data.equalsIgnoreCase("q"));
    }

    private static void retrieveStatisticsData(String data) {
        switch (data){
            case "a":{
                new StatisticsService().customersWhoBuyHorrorTicetsMoreThanThree();
                break;
            }
            case "b":{
                new StatisticsService().theMostProfitMovie();
                break;
            }
            case "c":{
                new StatisticsService().customersWhoCardExpiredAfterDate();
                break;
            }
            case "d":{
                new StatisticsService().theNewestAndOldestMovieWhichWatchMoreThanFourCustomers();
                break;
            }
            case "e":{
                new StatisticsService().wholeCinemaProfit();
                break;
            }
            case "f":{
                new StatisticsService().customersWhichBuyMovieTicket();
                break;
            }
            default:
                System.out.println("Niepopoprawny kod menu. Spróbuj jeszcze raz.");
        }
    }
}
