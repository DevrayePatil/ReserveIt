package org.example.reserveit.exceptions;

public class UnAuthorizedAccessException extends Exception {

    public UnAuthorizedAccessException() {
        super("UnAuthorized Access");
    }
    public UnAuthorizedAccessException(String message) {
        super(message);
    }
}
