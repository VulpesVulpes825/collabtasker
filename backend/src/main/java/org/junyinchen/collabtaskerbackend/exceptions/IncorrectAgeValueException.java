package org.junyinchen.collabtaskerbackend.exceptions;

/**
 * Custom exception class for handling incorrect age value errors within the application. This
 * exception is thrown when an age value provided to a method does not meet the expected criteria,
 * such as being below a minimum threshold or being logically inconsistent.
 */
public class IncorrectAgeValueException extends RuntimeException {
    /**
     * Constructs a new IncorrectAgeValueException with the specified detail message. The message
     * provides more information about the reason the exception was thrown.
     *
     * @param errorMessage the detail message
     */
    public IncorrectAgeValueException(String errorMessage) {
        super(errorMessage);
    }
}
