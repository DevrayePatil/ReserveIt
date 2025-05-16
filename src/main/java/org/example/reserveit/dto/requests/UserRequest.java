package org.example.reserveit.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.reserveit.models.UserType;

@Getter
@Setter
public class UserRequest {
    @NotNull(message = "User name must not empty")
    private String name;
    @NotNull(message = "User email must not empty")
    @Email(message = "Please provide valid email")
    private String email;
    private Long phoneNumber;
    private String password;
    private UserType type;
}
