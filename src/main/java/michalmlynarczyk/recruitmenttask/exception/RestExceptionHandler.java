package michalmlynarczyk.recruitmenttask.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceLayerException.class)
    public ResponseEntity<Object> handleServiceLayerException(ServiceLayerException ex) {
        if (ex.getCause() != null) {
            return ResponseEntity.unprocessableEntity().body(ex.getCause().getMessage());
        }
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return new ResponseEntity<>(
                Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(),
                headers,
                status);
    }
}
