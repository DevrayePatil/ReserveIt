package org.example.reserveit.exceptions;

import org.example.reserveit.models.Feature;

import java.util.List;

public class FeatureNotSupportedByScreenException extends Exception {

    public FeatureNotSupportedByScreenException(List<Feature> features) {
        super("Some features not supported by screen: " + features);
    }

}
