package org.example.reserveit.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
@Setter
@Builder
public class ApiResponse<T> {
    @Schema(description = "HTTP status of the response", example = "200")
    private HttpStatus status;
    @Schema(description = "A message describing the result", example = "Success")
    private String message;
    private Integer results;
    @Schema(description = "Map of validation errors")
    private Map<String, String> errors;
    @Schema(description = "The actual response data")
    private T data;

    public static<T> ApiResponse<T> of(HttpStatus status, String message, T data) {

        return ApiResponse.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }
}
