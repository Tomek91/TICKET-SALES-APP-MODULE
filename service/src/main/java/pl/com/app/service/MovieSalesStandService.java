package pl.com.app.service;


import pl.com.app.model.MovieSalesStand;

import java.util.List;

public interface MovieSalesStandService {
    List<MovieSalesStand> findByCustomerId(Integer customerId);
    void deleteAll();
    void deleteByCustom(String columnName, Object columnValue);
}
