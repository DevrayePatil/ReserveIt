package org.example.reserveit.exceptions;

public class AttributeNotFoundException extends RuntimeException {
    public AttributeNotFoundException(String attributeName) {
        super(attributeName + " must not be null or empty");
    }

}
