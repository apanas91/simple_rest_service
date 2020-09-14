package service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsExceptions extends RuntimeException{
    public UserAlreadyExistsExceptions(String message) {
        super(message);
    }

    public UserAlreadyExistsExceptions() {
    }
}
