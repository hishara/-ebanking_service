package au.com.anz.wholesale.banking.backend.exception;

import lombok.Getter;

/**
 * Custom error object to represent resource not found.
 */
@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;

    /**
     * Constructor for the class.
     * @param errorCode errorCode.
     * @param errorMessage errorMessage.
     */
    public ResourceNotFoundException(final String errorCode, final String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
