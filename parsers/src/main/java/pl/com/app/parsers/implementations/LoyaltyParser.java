package pl.com.app.parsers.implementations;

import pl.com.app.model.LoyaltyCard;
import pl.com.app.parsers.interfaces.Parser;
import pl.com.app.parsers.interfaces.RegularExpressions;

import java.math.BigDecimal;
import java.time.LocalDate;


public class LoyaltyParser implements Parser<LoyaltyCard> {
    @Override
    public LoyaltyCard parse(String line) {

        String[] arr = Parser.splitLine(line, RegularExpressions.LOYALTY_CARD, ",", "LOYALTY_CARD");

        return LoyaltyCard.builder()
                .expirationDate(LocalDate.parse(arr[0]))
                .discount(new BigDecimal(arr[1]))
                .moviesNumber(Integer.valueOf(arr[2]))
                .build();
    }
}
