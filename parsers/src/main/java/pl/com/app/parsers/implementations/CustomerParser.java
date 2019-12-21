package pl.com.app.parsers.implementations;

import pl.com.app.model.Customer;
import pl.com.app.parsers.interfaces.Parser;
import pl.com.app.parsers.interfaces.RegularExpressions;

public class CustomerParser implements Parser<Customer> {
    @Override
    public Customer parse(String line) {

        String[] arr = Parser.splitLine(line, RegularExpressions.CUSTOMER, ",", "CUSTOMER");

        Integer loyaltyCardId = null;
        if (arr.length > 4){
            loyaltyCardId = Integer.parseInt(arr[4]);
        }

        return Customer.builder()
                .name(arr[0])
                .surname(arr[1])
                .age(Integer.parseInt(arr[2]))
                .email(arr[3])
                .loyaltyCardId(loyaltyCardId)
                .build();
    }
}
