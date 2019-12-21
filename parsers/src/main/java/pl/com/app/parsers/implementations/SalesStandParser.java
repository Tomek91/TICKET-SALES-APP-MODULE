package pl.com.app.parsers.implementations;

import pl.com.app.model.SalesStand;
import pl.com.app.parsers.interfaces.Parser;
import pl.com.app.parsers.interfaces.RegularExpressions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SalesStandParser implements Parser<SalesStand> {

    @Override
    public SalesStand parse(String line) {
        String[] arr = Parser.splitLine(line, RegularExpressions.SALES_STAND, ",", "SALES_STAND");

        return SalesStand.builder()
                .customerId(Integer.valueOf(arr[0]))
                .movieId(Integer.valueOf(arr[1]))
                .startDateTime(LocalDateTime.parse(arr[2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

}