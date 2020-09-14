package service.handler;

import service.exceptions.RecordAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AwesomeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(org.springframework.dao.InvalidDataAccessApiUsageException.class)
    protected ResponseEntity<AwesomeException> handleThereIsNoSuchUserException() {
        return new ResponseEntity<>(new AwesomeException("There is no such user"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RecordAlreadyExistsException.class)
    protected ResponseEntity<AwesomeException> handleRecordAlreadyExistsException() {
        return new ResponseEntity<>(new AwesomeException("Record is already exists"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(io.jsonwebtoken.ExpiredJwtException.class)
    protected ResponseEntity<AwesomeException> handleExpiredJwtException() {
        return new ResponseEntity<>(new AwesomeException("Token is expired"), HttpStatus.UNAUTHORIZED);
    }

    @Data
    @AllArgsConstructor
    private static class AwesomeException {
        private String message;
    }
}