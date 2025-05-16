package org.example.reserveit.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieRequest {
    @NotNull
    private String name;
    private String description;
}
