package org.junyinchen.collabtaskerbackend.exceptions;

public class IncorrectAgeValueException extends RuntimeException {
    public IncorrectAgeValueException(String errorMessage) {
        super(errorMessage);
    }
}
