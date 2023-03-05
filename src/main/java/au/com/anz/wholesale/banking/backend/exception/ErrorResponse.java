package au.com.anz.wholesale.banking.backend.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Error response which is sent to the user when an error occures.
 */
@Builder
@Getter
public class ErrorResponse {
    private final String errorCode;
    private final String errorMessage;
    private final LocalDateTime timestamp;
}
