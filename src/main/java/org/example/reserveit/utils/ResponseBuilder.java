package org.example.reserveit.utils;

import org.example.reserveit.dto.responses.ApiResponse;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class ResponseBuilder {

    public static<T> ApiResponse<T> success(HttpStatus status, String message, T data, Integer results) {
        return ApiResponse.<T>builder()
                .status(status)
                .results(results)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> ok(String message, T data, Integer results) {
        return success(HttpStatus.OK, message, data, results);
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return success(HttpStatus.OK, message, data, null);
    }

    public static <T> ApiResponse<T> created(String message, T data) {
        return success(HttpStatus.CREATED, message, data, null);
    }


    public static ApiResponse<Object> error(HttpStatus status, String message, Map<String, String> errors) {
        return ApiResponse.builder()
                .status(status)
                .message(message)
                .errors(errors)
                .build();

    }

    public static ApiResponse<Object> error(HttpStatus status, String message) {
        return ApiResponse.builder()
                .status(status)
                .message(message)
                .build();
    }
}
