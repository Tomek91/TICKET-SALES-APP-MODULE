package pl.com.app.parsers.implementations;

import pl.com.app.exceptions.MyException;
import pl.com.app.parsers.interfaces.Parser;

public class IntegerParser implements Parser<Integer> {
    @Override
    public Integer parse(String line) {
        Integer value;
        try{
            value = Integer.valueOf(line);
        } catch (Exception e){
            throw new MyException("INTEGER PARSE ERROR");
        }
        return value;
    }
}
