package au.com.anz.wholesale.banking.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * Global exception handler to handle errors.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles resource not found exception.
     * @param exception exception.
     * @param request request.
     * @return ResponseEntity<ErrorResponse> response.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        ErrorResponse error = ErrorResponse.builder()
                .errorCode(exception.getErrorCode())
                .errorMessage(exception.getErrorMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * handles exception as  a whole.
     * @param exception exception.
     * @param request request.
     * @return ResponseEntity<ErrorResponse>.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception, WebRequest request) {
        ErrorResponse error = ErrorResponse.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .errorMessage(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
