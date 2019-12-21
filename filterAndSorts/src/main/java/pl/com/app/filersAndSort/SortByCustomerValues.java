package pl.com.app.filersAndSort;


import java.util.List;

public interface SortByCustomerValues<T> {

    List<T> sortByAge(List<T> customers);
    List<T> sortByEmail(List<T> customers);
    List<T> sortBySurname(List<T> customers);
    List<T> sortByName(List<T> customers);
}
