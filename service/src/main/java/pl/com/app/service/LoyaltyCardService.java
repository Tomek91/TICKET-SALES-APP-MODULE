package pl.com.app.service;


import pl.com.app.model.LoyaltyCard;

import java.util.List;
import java.util.Optional;

public interface LoyaltyCardService {
    void addAll(List<LoyaltyCard> items);
    void deleteAll();
    void deleteOne(Integer id);
    void addLoyaltyCard(LoyaltyCard item);
    Optional<LoyaltyCard> findById(Integer id);
}
