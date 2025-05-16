package org.example.reserveit.exceptions;

public class MissingFeaturesException extends RuntimeException {
    public MissingFeaturesException() {
        super("Screen must have at least one feature");
    }
}
