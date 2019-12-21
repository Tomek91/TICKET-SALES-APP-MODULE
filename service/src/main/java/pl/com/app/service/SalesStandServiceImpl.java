package pl.com.app.service;

import pl.com.app.exceptions.MyException;
import pl.com.app.model.SalesStand;
import pl.com.app.repository.SalesStandRepository;
import pl.com.app.repository.SalesStandRepositoryImpl;

import java.util.List;

public class SalesStandServiceImpl implements SalesStandService{
    SalesStandRepository salesStandRepository = new SalesStandRepositoryImpl();

    @Override
    public void addAll(List<SalesStand> items) {
        try {
            if (items == null) {
                throw new NullPointerException("MOVIES IS NULL");
            }
            salesStandRepository.addAll(items);

        } catch (Exception e) {
            throw new MyException("SALES STANDS SERVICE, ADD ALL");
        }
    }

    @Override
    public void deleteAll() {
        try {
            salesStandRepository.deleteAll();

        } catch (Exception e) {
            throw new MyException("SALES STAND SERVICE, DELETE ALL");
        }
    }

    @Override
    public void deleteOne(Integer id) {
        try {
            if (id == null){
                throw new NullPointerException("ID IS NULL");
            }
            salesStandRepository.delete(id);
        } catch (Exception e) {
            throw new MyException("SALES STAND SERVICE, DELETE ONE");
        }
    }

    @Override
    public void deleteByCustom(String columnName, Object columnValue) {
        try {
            if (columnName == null || columnValue == null) {
                throw new NullPointerException("CONDITION IS NULL");
            }
            salesStandRepository.deleteByCustom(columnName, columnValue);
        } catch (Exception e) {
                throw new MyException("SALES STAND SERVICE, DELETE BY CUSTOM");
        }
    }

    @Override
    public void addSalesStand(SalesStand salesStand) {
        try {
            if (salesStand == null) {
                throw new NullPointerException("SALES STAND IS NULL");
            }
            salesStandRepository.add(salesStand);

        } catch (Exception e) {
            throw new MyException("SALES STAND SERVICE, ADD SALES STAND");
        }
    }

    @Override
    public Integer numberOfMoviesForCustomers(Integer customerId) {
        Integer numberOfMovie = 0;
        try {
            if (customerId == null) {
                throw new NullPointerException("ID IS NULL");
            }
            numberOfMovie = (int)salesStandRepository.findAll()
                    .stream()
                    .filter(x -> x.getCustomerId() == customerId)
                    .count();

        } catch (Exception e) {
            throw new MyException("SALES STAND SERVICE, NUMBER OF SALES STAND FOR CUSTOMERS");
        }
        return numberOfMovie;
    }

    @Override
    public Integer getInsertedId() {
        Integer insertedId = 0;
        try {
            insertedId = salesStandRepository.getInsertedId();

        } catch (Exception e) {
            throw new MyException("SALES STAND SERVICE, LAST INSERTED ID");
        }
        return insertedId;
    }
}
