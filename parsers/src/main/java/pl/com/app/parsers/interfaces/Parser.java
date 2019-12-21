package pl.com.app.parsers.interfaces;

import pl.com.app.exceptions.MyException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FunctionalInterface
public interface Parser<T> {
    T parse(final String line);

    static String[] splitLine(final String line, final String regex, final String splitBy, final String className){
        if (!Parser.isLineCorrect(line, regex)) {
            throw new IllegalArgumentException(className + " FILE PARSE EXCEPTION");
        }
        return line.split(splitBy);
    }

    static boolean isLineCorrect(final String line, final String regex) {

        return line != null && line.matches(regex);
    }

    static <T> List<T> parseFile(String filename, Parser<T> parser) {
        try (Stream<String> lines = Files.lines(Paths.get(filename))) {
            return lines
                    .map(parser::parse)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MyException("FILE PARSE EXCEPTION");
        }
    }
}
