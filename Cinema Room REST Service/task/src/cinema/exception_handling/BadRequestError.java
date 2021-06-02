package cinema.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestError extends RuntimeException {
    public BadRequestError(String message) {
        super(message);
    }
}
