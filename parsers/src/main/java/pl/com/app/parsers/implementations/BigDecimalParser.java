package pl.com.app.parsers.implementations;

import pl.com.app.exceptions.MyException;
import pl.com.app.parsers.interfaces.Parser;

import java.math.BigDecimal;

public class BigDecimalParser implements Parser<BigDecimal>{

    @Override
    public BigDecimal parse(String line) {
        BigDecimal value;
        try{
            value = new BigDecimal(line);
        } catch (Exception e){
            throw new MyException("BIG DECIMAL PARSE ERROR");
        }
        return value;
    }
}
