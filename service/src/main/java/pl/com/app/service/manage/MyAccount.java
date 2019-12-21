package pl.com.app.service.manage;

import pl.com.app.connection.DataReader;
import pl.com.app.exceptions.MyException;
import pl.com.app.filersAndSort.FilterBy;
import pl.com.app.filersAndSort.FilterByImpl;
import pl.com.app.model.*;
import pl.com.app.senders.EmailUtil;
import pl.com.app.service.*;
import pl.com.app.service.menu.Converts;
import pl.com.app.service.menu.Menu;
import pl.com.app.service.menu.TicketSalesSimulator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MyAccount {

    private CustomerService customerService = new CustomerServiceImpl();
    private SalesStandService salesStandService = new SalesStandServiceImpl();
    private MovieService movieService = new MovieServiceImpl();
    private MovieSalesStandService movieSalesStandService = new MovieSalesStandServiceImpl();
    private LoyaltyCardService loyaltyCardService = new LoyaltyCardServiceImpl();
    private Customer customer = new Customer();
    private DataReader dataReader = new DataReader();

    public void myAccount() {
        System.out.println(TicketSalesSimulator.PREFIX);
        System.out.println("Podaj swoje dane ... \nimie?");
        String name = dataReader.getString();
        System.out.println("nazwisko?");
        String surname = dataReader.getString();
        System.out.println("email?");
        String email = dataReader.getString();
        MyAccount myAccount = new MyAccount();
        if (myAccount.doesCustomerExist(name, surname, email)){
            System.out.println("Poprawnie zweryfikowano dane klienta.");
            String data = null;
            do {
                System.out.println(TicketSalesSimulator.PREFIX);
                System.out.println("Wybierz jedną z dostępnych opcji...");
                Menu menu = Menu.builder().menuList(Menu.createAccountMenu()).build();
                System.out.println(Menu.showMenu(menu.getMenuList()));
                data = dataReader.getString();
                System.out.println(TicketSalesSimulator.PREFIX);
                switch (data){
                    case "k":{
                        myAccount.buyTicket();
                        break;
                    }
                    case "h":{
                        myAccount.history();
                        break;
                    }
                    default:
                        System.out.println("Niepopoprawny kod menu. Spróbuj jeszcze raz.");
                }
            } while (!data.equalsIgnoreCase("q"));
        } else {
            System.out.println(TicketSalesSimulator.PREFIX);
            System.out.println("podany klient nie istnieje");
        }
    }

    public boolean doesCustomerExist(String name, String surname, String email){
        boolean retValue = false;
        Optional<Customer> customerOpt = customerService.findByNameSurnameAndEmail(name, surname, email);
        if (customerOpt.isPresent()){
            this.customer = customerOpt.get();
            retValue = true;
        }
        return retValue;
    }

    public void history(){
        List<MovieSalesStand> movieSalesStands = movieSalesStandService.findByCustomerId(customer.getId());
        System.out.println("Wykaz wszystkich zakupionych do tej pory biletów");
        System.out.println(Converts.getOrAbsence(movieSalesStands));
        if (!movieSalesStands.isEmpty()){
            filterData(movieSalesStands);
        }
    }

    private void filterData(List<MovieSalesStand> movieSalesStands) {
        String data = null;
        do {
            System.out.println("Czy chcesz przefiltrować wyniki? (t - tak / n - nie)");
            data = dataReader.getString();
            if (data.equalsIgnoreCase("t")) {
                do {
                    System.out.println(TicketSalesSimulator.PREFIX);
                    System.out.println("Wybierz pozycję menu...");
                    Menu menu = Menu.builder().menuList(Menu.createFilterMenu()).build();
                    System.out.println(Menu.showMenu(menu.getMenuList()));
                    data = dataReader.getString();
                    System.out.println(TicketSalesSimulator.PREFIX);
                    String historyInfo = chooseFilter(data, movieSalesStands);
                    if (historyInfo != null){
                        System.out.println(historyInfo);
                        EmailUtil.send(customer.getEmail(),"Historia zamowień", historyInfo);
                    }
                } while (!data.equalsIgnoreCase("q"));
            } else if (!data.equalsIgnoreCase("n")) {
                System.out.println(TicketSalesSimulator.PREFIX);
                System.out.println("Niepopoprawny kod menu. Spróbuj jeszcze raz.");
            }
        } while (!(data.equalsIgnoreCase("n") || data.equalsIgnoreCase("q")));
    }

    private String chooseFilter(String filter, List<MovieSalesStand> movieSalesStands) {
        FilterBy<MovieSalesStand> filterBy = new FilterByImpl();
        switch (filter) {
            case "w": {
                String genre = getGenreFilter();
                System.out.println(String.join(" ","Wykupione bilety na filmy z gatunku", genre, "..."));
                return Converts.getOrAbsence(filterBy.filterByGenre(genre, movieSalesStands));
            }
            case "x": {
                LocalDate dateFrom = getDateFromFilter();
                LocalDate dateTo = getDateToFilter();
                System.out.println(String.join(" ", "Wykupione bilety na filmy z przedziału od", dateFrom.toString(), "do", dateTo.toString()));
                return Converts.getOrAbsence(filterBy.filterByDates(dateFrom, dateTo, movieSalesStands));
            }
            case "y": {
                Integer duration = getDurationFilter();
                System.out.println(String.join(" ","Wykupione bilety na filmy o dlugości", duration.toString(), "..."));
                return Converts.getOrAbsence(filterBy.filterByDuration(duration, movieSalesStands));
            }
            case "z": {
                String genre = getGenreFilter();
                LocalDate dateFrom = getDateFromFilter();
                LocalDate dateTo = getDateToFilter();
                Integer duration = getDurationFilter();
                System.out.println(String.join(" ",
                        "Wykupione bilety na filmy z gatunku",
                        genre,
                        ", z przedziału od",
                        dateFrom.toString(),
                        "do",
                        dateTo.toString(),
                        ", o dlugości",
                        duration.toString(),
                        "...")
                );
                return Converts.getOrAbsence(filterBy.filterByAll(genre, dateFrom, dateTo, duration, movieSalesStands));
            }
            case "q": {
                return null;
            }
            default:
                System.out.println("Niepopoprawnie wprowadzone dane.");
                return null;
        }
    }

    private String getGenreFilter(){
        System.out.println("Podaj gatunek filmu:");
        String genre = dataReader.getString();
        System.out.println(TicketSalesSimulator.PREFIX);
        return genre;
    }

    private LocalDate getDateFromFilter(){
        System.out.println("Podaj datę od:");
        return dataReader.getLocalDate();
    }

    private LocalDate getDateToFilter(){
        System.out.println("Podaj datę do:");
        LocalDate localDate = dataReader.getLocalDate();
        System.out.println(TicketSalesSimulator.PREFIX);
        return localDate;
    }

    private Integer getDurationFilter(){
        System.out.println("Podaj czas trwania filmu:");
        Integer duration = dataReader.getInteger();
        System.out.println(TicketSalesSimulator.PREFIX);
        return duration;
    }

    public void buyTicket(){
        List<Movie> movies = movieService.findAll();
        if (movies.isEmpty()){
            System.out.println("Brak filmów!!!");
        } else {
            if (LocalTime.now().isAfter(LocalTime.of(22, 0 , 0))){
                System.out.println("Przepraszamy, dzisiaj kino już jest zamknięte.");
                return;
            }
            System.out.println("Dostępny repertuar kina:");
            for (int i = 0; i < movies.size(); i++) {
                System.out.println(String.join(" - ", String.valueOf(i), movies.get(i).toString()));
            }
            Integer movieIndex = chooseMovie(movies.size());
            Map<String, LocalTime> availableHours = generateAvailableHours();
            String hourIndex = chooseHour(availableHours);
            BigDecimal ticketPrice = ticketPrice(customer.getLoyaltyCardId(), movies.get(movieIndex));
            String salesInfo = salesInfo(movies.get(movieIndex).getTitle(), availableHours.get(hourIndex).toString(), ticketPrice.toString());
            System.out.println(salesInfo);
            EmailUtil.send(customer.getEmail(),"Informacja o zakupionym bilecie", salesInfo);
            addSaleStand(movies.get(movieIndex).getId(), LocalDateTime.of(LocalDate.now(), availableHours.get(hourIndex)));
            if (salesStandService.numberOfMoviesForCustomers(customer.getId()) == MovieConstant.NUMBER_OF_MOVIES_TO_LOYALTY_CARD){
                loyaltyCardEvent();
            }
        }
    }

    private String chooseHour(Map<String, LocalTime> availableHours) {
        System.out.println("Wybierz godzinę na jaką rezerwujesz bilet....");
        availableHours.forEach((k, v) -> System.out.println(String.join(" - ", k, v.toString())));
        String hourIndex = dataReader.getString();
        try{
            if (!availableHours.containsKey(hourIndex)){
                throw new IllegalArgumentException("WRONG HOUR INDEX");
            }
        } catch (Exception e){
            throw new MyException("TICKET BUY, HOUR INDEX ERROR");
        }
        return hourIndex;
    }

    private Integer chooseMovie(int movieListSize) {
        System.out.println("Proszę wybrać film...");
        Integer movieIndex = dataReader.getInteger();
        try{
            if (movieIndex < 0 || movieIndex > movieListSize - 1){
                throw new IndexOutOfBoundsException("WRONG MOVIE INDEX");
            }
        } catch (Exception e){
            throw new MyException("TICKET BUY, MOVIE INDEX ERROR");
        }
        return movieIndex;
    }

    private void loyaltyCardEvent() {
        String data = null;
        do{
            System.out.println(TicketSalesSimulator.PREFIX);
            System.out.println("Czy chcesz otrzymać kartę stałego klienta? t - tak / n - nie");
            data = dataReader.getString();
            switch (data){
                case "t":{
                    addLoyaltyCard();
                    return;
                }
                case "n":{
                    break;
                }
                default:
                    System.out.println("Niepopoprawny kod. Spróbuj jeszcze raz.");
            }
        }while(!data.equalsIgnoreCase("n"));
    }

    private void addLoyaltyCard() {
        loyaltyCardService.addLoyaltyCard(
                LoyaltyCard.builder()
                .expirationDate(LocalDate.now().plusMonths(6))
                .moviesNumber(MovieConstant.NUMBER_OF_MOVIES_WITH_DISCOUNT)
                .discount(new BigDecimal(15))
                .build()
        );
        Integer insertedSalesStandId = salesStandService.getInsertedId();
        this.customer.setLoyaltyCardId(insertedSalesStandId);
        customerService.updateOne(customer);
        System.out.println("Karta stałego klienta została dodana.");
    }

    private void addSaleStand(Integer movieId, LocalDateTime releaseDate) {
        salesStandService.addSalesStand(SalesStand.builder()
                .customerId(customer.getId())
                .movieId(movieId)
                .startDateTime(releaseDate)
                .build()
        );
    }

    private String salesInfo(String title, String hour, String price) {
        return String.join(" ",
                "Kupiłeś bilet na film:",
                title,
                ", na dzisiaj na godzine:",
                hour,
                ", cena biletu to:",
                price
        );
    }

    private Map<String, LocalTime> generateAvailableHours(){
        Map<String, LocalTime> availableHours = new LinkedHashMap<>();
        LocalTime movieAvailableHour = LocalTime.of(LocalTime.now().getHour() + 1, 0, 0);
        if (movieAvailableHour.isBefore(LocalTime.of(8, 0, 0))){
            movieAvailableHour = LocalTime.of(8, 0, 0);
        }
        int aIndex = 'a';
        while (movieAvailableHour.isBefore(LocalTime.of(23 ,0, 0))){
            availableHours.put(String.valueOf((char)aIndex++), movieAvailableHour);
            movieAvailableHour = movieAvailableHour.plusMinutes(30);
        }
        return availableHours;
    }

    private BigDecimal ticketPrice(Integer loyaltyCardId, Movie movie){
        BigDecimal ticketPrice = null;
        if (loyaltyCardId == null || loyaltyCardId == 0 || !isActiveLoyaltyCard(loyaltyCardId)){
            ticketPrice = movie.getPrice();
        } else {
            LoyaltyCard loyaltyCard = getLoyaltyCard(loyaltyCardId);
            ticketPrice = movie.getPrice()
                    .multiply(new BigDecimal(100).subtract(loyaltyCard.getDiscount()))
                    .divide(new BigDecimal(100), 2, RoundingMode.FLOOR);
        }
        return ticketPrice;
    }

    private LoyaltyCard getLoyaltyCard(Integer loyaltyCardId){
        return loyaltyCardService
                .findById(loyaltyCardId)
                .orElseThrow(NullPointerException::new);
    }

    private boolean isActiveLoyaltyCard(Integer loyaltyCardId) {
        LoyaltyCard loyaltyCard = getLoyaltyCard(loyaltyCardId);
        return LocalDate.now().isBefore(loyaltyCard.getExpirationDate())
                && loyaltyCard.getMoviesNumber() > salesStandService.numberOfMoviesForCustomers(customer.getId()) - MovieConstant.NUMBER_OF_MOVIES_TO_LOYALTY_CARD;
    }
}
