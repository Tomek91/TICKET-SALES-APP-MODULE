package pl.com.app.service.menu;


import pl.com.app.connection.DataReader;
import pl.com.app.connection.DbConnection;
import pl.com.app.exceptions.MyException;
import pl.com.app.service.DataInitializeService;
import pl.com.app.service.DataInitializeServiceImpl;
import pl.com.app.service.manage.*;

public class TicketSalesSimulator {
    private DataReader dataReader = new DataReader();
    public static final String PREFIX = "<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>";

    public void ticketSalesSimulator() {
        System.out.println("Witaj w symulatorze sprzedaży biletów !!!");
        System.out.println("Oto Twoje menu. Każda liczba odpowiada akcji, którę chcesz wykonać.\n" +
                "Pamiętaj, w każdej chwili możesz skończyć aplikację naciskając 'q'.");

        try{
            Menu menu = Menu.builder().menuList(Menu.createMenu()).build();
            String data = null;
            do {
                System.out.println(TicketSalesSimulator.PREFIX);
                System.out.println(Menu.showMenu(menu.getMenuList()));
                System.out.println("Wybierz pozycję menu...");
                data = dataReader.getString();
                retrieveData(data);
            } while (!data.equalsIgnoreCase("q"));
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }
    }

    private void retrieveData(String data) {
        switch (data){
            case "1":{
                new AddCustomer().addCustomer();
                break;
            }
            case "2":{
                new AddMovie().addMovies();
                break;
            }
            case "3":{
                loadDefaultData();
                break;
            }
            case "4":{
                Manage.manageMoviesCustomers("Zarządzanie klinetami.", new CustomerManageImpl());
                break;
            }
            case "5":{
                Manage.manageMoviesCustomers("Zarządzanie filmami.", new MovieManageImpl());
                break;
            }
            case "6":{
                new MyAccount().myAccount();
                break;
            }
            case "7":{
                StatisticsService.statistics();
                break;
            }
            case "q":{
                System.out.println("Koniec programu.");
                DbConnection.getInstance().close();
                DataReader.close();
                break;
            }
            default:
                System.out.println("Niepopoprawny kod menu. Spróbuj jeszcze raz.");
        }
    }

    private void loadDefaultData() {
        System.out.println("Ładowanie domyślnych danych...");
        DataInitializeService dataInitializeService = new DataInitializeServiceImpl();
        dataInitializeService.initialize();
        System.out.println("Pomyślnie załadowano dane do bazy danych.");
    }
}
