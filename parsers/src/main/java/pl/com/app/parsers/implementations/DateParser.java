package pl.com.app.parsers.implementations;

import pl.com.app.exceptions.MyException;
import pl.com.app.parsers.interfaces.Parser;

import java.time.LocalDate;

public class DateParser implements Parser<LocalDate> {

    @Override
    public LocalDate parse(String dateInString) {
        LocalDate date;
        try{
            date = LocalDate.parse(dateInString);
        } catch (Exception e){
            throw new MyException("DATE PARSE ERROR");
        }
        return date;
    }
}
