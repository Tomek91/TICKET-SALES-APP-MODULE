package pl.com.app.service.menu;

import pl.com.app.connection.DataReader;
import pl.com.app.model.Customer;
import pl.com.app.service.CustomerService;
import pl.com.app.service.CustomerServiceImpl;

public class AddCustomer {
    private DataReader dataReader = new DataReader();

    public void addCustomer() {
        System.out.println("Dodawanie nowego klienta");
        CustomerService customerService = new CustomerServiceImpl();
        customerService.addCustomer(getCustomerToAdd());
        System.out.println("Klient został dodany.");
    }

    private Customer getCustomerToAdd() {
        System.out.println("Podaj dane klienta.\nImię:");
        String name = dataReader.getString();
        System.out.println("Nazwisko:");
        String surname = dataReader.getString();
        System.out.println("Wiek:");
        Integer age = dataReader.getInteger();
        System.out.println("E-mail:");
        String email = dataReader.getString();
        return Customer.builder().name(name).surname(surname).age(age).email(email).build();
    }
}
