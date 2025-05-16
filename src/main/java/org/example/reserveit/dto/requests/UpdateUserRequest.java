package org.example.reserveit.dto.requests;

import lombok.Value;
import org.example.reserveit.models.UserType;

import java.io.Serializable;

/**
 * DTO for {@link org.example.reserveit.models.User}
 */
@Value
public class UpdateUserRequest implements Serializable {
    String name;
    String email;
    Long phoneNumber;
    UserType type;
}
