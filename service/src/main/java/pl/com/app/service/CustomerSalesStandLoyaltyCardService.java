package pl.com.app.service;

public interface CustomerSalesStandLoyaltyCardService {
    void deleteAll();
    void deleteByCustom(String columnName, Object columnValue);
}
