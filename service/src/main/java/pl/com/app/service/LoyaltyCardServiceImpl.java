package pl.com.app.service;

import pl.com.app.exceptions.MyException;
import pl.com.app.model.LoyaltyCard;
import pl.com.app.repository.LoyaltyCardRepository;
import pl.com.app.repository.LoyaltyCardRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class LoyaltyCardServiceImpl implements LoyaltyCardService {

    LoyaltyCardRepository loyaltyCardRepository = new LoyaltyCardRepositoryImpl();

    @Override
    public void addAll(List<LoyaltyCard> items) {
        try {
            if (items == null) {
                throw new NullPointerException("LOYALTY CARDS IS NULL");
            }
            loyaltyCardRepository.addAll(items);

        } catch (Exception e) {
            throw new MyException("LOYALTY CARD SERVICE, ADD ALL");
        }
    }

    @Override
    public void deleteAll() {
        try {
            loyaltyCardRepository.deleteAll();

        } catch (Exception e) {
            throw new MyException("LOYALTY CARD SERVICE, DELETE ALL");
        }
    }

    @Override
    public void deleteOne(Integer id) {
        try {
            if (id == null){
                throw new NullPointerException("ID IS NULL");
            }
            loyaltyCardRepository.delete(id);
        } catch (Exception e) {
            throw new MyException("LOYALTY CARD SERVICE, DELETE ONE");
        }
    }

    @Override
    public void addLoyaltyCard(LoyaltyCard item) {
        try {
            if (item == null) {
                throw new NullPointerException("LOYALTY CARD IS NULL");
            }
            loyaltyCardRepository.add(item);

        } catch (Exception e) {
            throw new MyException("LOYALTY CARD SERVICE, ADD LOYALTY CARD");
        }
    }

    @Override
    public Optional<LoyaltyCard> findById(Integer id) {
        Optional<LoyaltyCard> loyaltyCardOpt = Optional.empty();
        try {
            if (id == null) {
                throw new NullPointerException("ID IS NULL");
            }
            loyaltyCardOpt = loyaltyCardRepository.findOneById(id);

        } catch (Exception e) {
            throw new MyException("LOYALTY CARD SERVICE, FIND BY ID");
        }
        return loyaltyCardOpt;
    }
}
