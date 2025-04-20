package zoo.web.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import zoo.domain.exceptions.ZooException;

/**
 * Handler для ZooException.
 */
@RestControllerAdvice
public class ZooExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ZooException.class)
    public ResponseEntity<Object> handleException(ZooException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}