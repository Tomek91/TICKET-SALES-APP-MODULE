package pl.com.app.service;

import pl.com.app.connection.DbConnection;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.Customer;
import pl.com.app.repository.*;

import java.util.List;

public class CustomerSalesStandLoyaltyCardServiceImpl implements CustomerSalesStandLoyaltyCardService {
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private SalesStandRepository salesStandRepository = new SalesStandRepositoryImpl();
    private LoyaltyCardRepository loyaltyCardRepository = new LoyaltyCardRepositoryImpl();

    @Override
    public void deleteAll() {
        try {
            DbConnection.connection().setAutoCommit(false);
            customerRepository.deleteAll();
            salesStandRepository.deleteAll();
            loyaltyCardRepository.deleteAll();

            DbConnection.connection().commit();
            DbConnection.connection().setAutoCommit(true);
        } catch (Exception e) {
            try {
                DbConnection.connection().rollback();
            } catch (Exception ee) {
                throw new MyException("CUSTOMER SALES_STAND LOYALTY CARD SERVICE ROLLBACK EXCEPTION");
            }
            throw new MyException("CUSTOMER SALES_STAND LOYALTY CARD SERVICE, DELETE ALL");
        }
    }

    @Override
    public void deleteByCustom(String columnName, Object columnValue) {
        try {
            if (columnName == null || columnValue == null) {
                throw new NullPointerException("CONDITION IS NULL");
            }
            DbConnection.connection().setAutoCommit(false);

            List<Customer> customers = customerRepository.findByCustom(columnName, columnValue);
            for (Customer c : customers){
                customerRepository.delete(c.getId());
                salesStandRepository.deleteByCustom("customer_id", c.getId());
                if (c.getLoyaltyCardId() != null){
                    loyaltyCardRepository.delete(c.getLoyaltyCardId());
                }
            }

            DbConnection.connection().commit();
            DbConnection.connection().setAutoCommit(true);
        } catch (Exception e) {
            try {
                DbConnection.connection().rollback();
            } catch (Exception ee) {
                throw new MyException("CUSTOMER SALES_STAND LOYALTY CARD SERVICE ROLLBACK EXCEPTION");
            }
            throw new MyException("CUSTOMER SALES_STAND LOYALTY CARD SERVICE, DELETE BY CUSTOM");
        }
    }
}
