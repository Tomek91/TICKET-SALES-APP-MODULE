package pl.com.app.connection;

import pl.com.app.parsers.implementations.BigDecimalParser;
import pl.com.app.parsers.implementations.DateParser;
import pl.com.app.parsers.implementations.IntegerParser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class DataReader {
    private static Scanner sc = new Scanner(System.in);

    public static void close() {
        sc.close();
    }

    public BigDecimal getBigDecimal(){
        return new BigDecimalParser().parse(sc.nextLine());
    }

    public Integer getInteger(){
        return new IntegerParser().parse(sc.nextLine());
    }

    public LocalDate getLocalDate(){
        return new DateParser().parse(sc.nextLine());
    }

    public String getString(){
        return sc.nextLine();
    }
}
