package pl.com.app.parsers.interfaces;

public interface RegularExpressions {
    String CUSTOMER = "[A-Z]+,[A-Z]+,\\d+,[a-z]+@[a-z]+\\.[a-z]+,?\\d*";
    String LOYALTY_CARD = "\\d{4}-\\d{2}-\\d{2},\\d+\\.\\d{2},\\d+";
    String MOVIES = "[A-Z ]+,[a-z]+,\\d{2}\\.\\d{2},\\d,\\d{4}-\\d{2}-\\d{2}";
    String SALES_STAND = "\\d+,\\d+,\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
}
