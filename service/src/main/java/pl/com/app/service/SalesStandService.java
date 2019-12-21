package pl.com.app.service;


import pl.com.app.model.SalesStand;

import java.util.List;

public interface SalesStandService {

    void addAll(List<SalesStand> items);
    void addSalesStand(SalesStand item);
    void deleteAll();
    void deleteOne(Integer id);
    void deleteByCustom(String columnName, Object columnValue);
    Integer numberOfMoviesForCustomers(Integer customerId);
    Integer getInsertedId();
}
