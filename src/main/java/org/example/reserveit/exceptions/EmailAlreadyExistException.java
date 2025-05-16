package org.example.reserveit.exceptions;

public class EmailAlreadyExistException extends Exception {

    public EmailAlreadyExistException() {
        super("User already available by this email");
    }
}
