package pl.com.app.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MyException extends RuntimeException {
    private String exceptionMessage;
    private LocalDateTime exceptionDateTime;

    public MyException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        exceptionDateTime = LocalDateTime.now();
    }

    @Override
    public String getMessage() {
        return String.join(" ", "[EXCEPTION]:", exceptionMessage, exceptionDateTime.toString());
    }
}
