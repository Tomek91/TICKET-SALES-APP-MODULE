package pl.com.app.service.manage;

import pl.com.app.connection.DataReader;
import pl.com.app.filersAndSort.SortByCustomerValues;
import pl.com.app.filersAndSort.SortByCustomerValuesImpl;
import pl.com.app.model.Customer;
import pl.com.app.service.CustomerSalesStandLoyaltyCardService;
import pl.com.app.service.CustomerSalesStandLoyaltyCardServiceImpl;
import pl.com.app.service.CustomerService;
import pl.com.app.service.CustomerServiceImpl;
import pl.com.app.service.menu.Converts;
import pl.com.app.service.menu.TicketSalesSimulator;

import java.util.List;
import java.util.Optional;


public class CustomerManageImpl implements Manage<Customer> {
    private CustomerService customerService = new CustomerServiceImpl();
    private CustomerSalesStandLoyaltyCardService customerSalesStandLoyaltyCardService = new CustomerSalesStandLoyaltyCardServiceImpl();
    private DataReader dataReader = new DataReader();

    @Override
    public void deleteByCustom() {
        System.out.println("Podaj według jakiego kryterium chcesz usunąć klientów.\nOto dostępne możliwosci:");
        System.out.println(getCriteriums());
        String criterium = dataReader.getString();
        while(!getCriteriums().contains(criterium)){
            System.out.println(TicketSalesSimulator.PREFIX);
            System.out.println("Niepopoprawny kod. Spróbuj jeszcze raz podać kryterium.");
            criterium = dataReader.getString();
        }
        System.out.println(String.join(" ","Podaj wartość dla", criterium, ":"));
        String criteriumValue = dataReader.getString();
        System.out.println("Usunięto klientów według kryterium.");
        customerSalesStandLoyaltyCardService.deleteByCustom(criterium, criteriumValue);
    }

    @Override
    public void deleteAll() {
        customerSalesStandLoyaltyCardService.deleteAll();
        System.out.println("Usunięto wszystkich klientów.");
    }

    @Override
    public void updateOne() {
        System.out.println("Wykaz wszytkich klientów:");
        List<Customer> customers = customerService.findAll();
        System.out.println(Converts.getOrAbsence(customers));
        if (!customers.isEmpty()){
            System.out.println("Podaj id klienta, którego chcesz modyfikować...");
            Integer id = dataReader.getInteger();
            Optional<Customer> customerOpt = customerService.findById(id);
            if (customerOpt.isPresent()){
                Customer customer = customerOpt.get();
                System.out.println("Dostępne możliwosći modyfikacji to:");
                System.out.println(getCriteriums());
                System.out.println("Podaj nazwe kolumny, którą chcesz zmienić wartość (q - kończy modyfikacje)");
                String columnName = null;
                do {
                    columnName = dataReader.getString();
                    setModificationCustomer(columnName, customer);
                }while (!columnName.equals("q") && !getCriteriums().contains(columnName));
                customerService.updateOne(customer);
                System.out.println("Zaktualizowano rekord");
            } else {
                System.out.println("Nie ma takiego rekordu.");
            }
        }
    }

    private void setModificationCustomer( String columnName, Customer customer){
        switch (columnName){
            case "name":{
                System.out.println("podaj nowe imie");
                customer.setName(dataReader.getString());
                break;
            }
            case "surname":{
                System.out.println("podaj nowe nazwisko");
                customer.setSurname(dataReader.getString());
                break;
            }
            case "age":{
                System.out.println("podaj nowy wiek");
                Integer age = dataReader.getInteger();
                customer.setAge(age);
                break;
            }
            case "email":{
                System.out.println("podaj nowy email");
                customer.setEmail(dataReader.getString());
                break;
            }
            default:
                System.out.println("nieporawna nazwa kolumny");
                break;
        }
    }

    @Override
    public void updateAll() {
        System.out.println("Modyfikacja wszystkich rekordów:");
        System.out.println("Wykaz wszytkich klientów:");
        List<Customer> customers = customerService.findAll();
        System.out.println(Converts.getOrAbsence(customers));
        if (!customers.isEmpty()) {
            System.out.println("Dostępne możliwosći modyfikacji klientów to:");
            System.out.println(getCriteriums());
            System.out.println("Podaj nazwe kolumny, którą chcesz zmienić wartość (q - kończy modyfikacje)");
            String columnName = null;
            Object columnValue = null;
            do {
                columnName = dataReader.getString();
                columnValue = getColumnValue(columnName);
            } while (!columnName.equals("q") && !getCriteriums().contains(columnName));
            customerService.updateAll(columnName, columnValue);
            System.out.println("Zaktualizowano rekordy");
        } else {
            System.out.println("Nie ma takiego rekordu.");
        }
    }

    private Object getColumnValue( String columnName) {
        switch (columnName) {
            case "name": {
                System.out.println("podaj nowe imie");
                return dataReader.getString();
            }
            case "surname": {
                System.out.println("podaj nowe nazwisko");
                return dataReader.getString();
            }
            case "age": {
                System.out.println("podaj nowy wiek");
                return dataReader.getInteger();
            }
            case "email": {
                System.out.println("podaj nowy email");
                return dataReader.getString();
            }
            default:
                System.out.println("nieporawna nazwa kolumny");
                return null;
        }
    }

    @Override
    public void findByCustom() {
        System.out.println("Podaj według jakiego kryterium chcesz wyświetlić klientów.\nOto dostępne możliwosci:");
        System.out.println(getCriteriums());
        String criterium = dataReader.getString();
        while(!getCriteriums().contains(criterium)){
            System.out.println(TicketSalesSimulator.PREFIX);
            System.out.println("Niepopoprawny kod. Spróbuj jeszcze raz podać kryterium.");
            criterium = dataReader.getString();
        }
        System.out.println(String.join(" ","Podaj wartość dla", criterium, ":"));
        String criteriumValue = dataReader.getString();
        List<Customer> customers = customerService.findByCustom(criterium, criteriumValue);
        System.out.println("Wykaz klientów według wybranego kryterium:");
        System.out.println(Converts.getOrAbsence(customers));
        if (!customers.isEmpty()){
            System.out.println(TicketSalesSimulator.PREFIX);
            sortBy(customers);
        }
    }

    @Override
    public void findAll() {
        System.out.println("Wykaz wszytkich klientów:");
        List<Customer> customers = customerService.findAll();
        System.out.println(Converts.getOrAbsence(customers));
        sortBy(customers);
    }

    @Override
    public void sortBy (List<Customer> customers){
        System.out.println("Według jakiego kryterium chcesz posortować dane? (q - wyjście)");
        System.out.println(getCriteriums());
        String sort = dataReader.getString();
        SortByCustomerValues<Customer> sortByCustomerValues = new SortByCustomerValuesImpl();
        switch (sort){
            case "name":{
                customers = sortByCustomerValues.sortByName(customers);
                break;
            }
            case "surname":{
                customers = sortByCustomerValues.sortBySurname(customers);
                break;
            }
            case "age":{
                customers = sortByCustomerValues.sortByAge(customers);
                break;
            }
            case "email":{
                customers = sortByCustomerValues.sortByEmail(customers);
                break;
            }
            case "q":{
                return;
            }
            default:
                System.out.println("Niepopoprawnie wprowadzone dane.");
                return;
        }
        System.out.println(Converts.getOrAbsence(customers));
    }


    public String getCriteriums() {
        return String.join("\n",
                "name",
                          "surname",
                          "age",
                          "email"
        );
    }
}
