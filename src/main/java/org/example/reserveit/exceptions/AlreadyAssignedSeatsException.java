package org.example.reserveit.exceptions;

import java.util.List;

public class AlreadyAssignedSeatsException extends Exception {
    public AlreadyAssignedSeatsException(List<Long> alreadyAssignedSeatIds) {
        super("Some seats re already assigned to another screen: " + alreadyAssignedSeatIds);
    }
}
