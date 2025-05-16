package org.example.reserveit.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.reserveit.dto.responses.ApiResponse;
import org.example.reserveit.utils.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Operation(summary = "Handle validation exceptions",
            description = "Processes MethodArgumentNotValidException")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400",
                    description = "Bad Request - Validation failed",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })


    @ExceptionHandler(AlreadyAssignedSeatsException.class)
    public ResponseEntity<ApiResponse<Object>> handleAlreadyAssignedSeatsException(AlreadyAssignedSeatsException e) {
        return buildResponse(CONFLICT, e.getMessage());
    }

    @ExceptionHandler(AttributeNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleAlreadyAttributeNotFoundException(AttributeNotFoundException e) {
        return buildResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(BookingAlreadyPaidException.class)
    public ResponseEntity<ApiResponse<Object>> handleBookingAlreadyPaidException(BookingAlreadyPaidException e) {
        return buildResponse(CONFLICT, e.getMessage());
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateEntityException(DuplicateEntityException e) {
        return buildResponse(CONFLICT, e.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ApiResponse<Object>> handleEmailAlreadyExistException(EmailAlreadyExistException e) {
        return buildResponse(CONFLICT, e.getMessage());
    }
    @ExceptionHandler(FeatureNotSupportedByScreenException.class)
    public ResponseEntity<ApiResponse<Object>> handleFeatureNotSupportedByScreenException(FeatureNotSupportedByScreenException e) {
        return buildResponse(UNPROCESSABLE_ENTITY, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        return buildResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidDateException(InvalidDateException e) {
        return buildResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MissingFeaturesException.class)
    public ResponseEntity<ApiResponse<Object>> handleMissingFeaturesException(MissingFeaturesException e) {
        return buildResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleMovieNotFoundException(MovieNotFoundException e) {
       return buildResponse(NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNoResourceFound(NoResourceFoundException e) {
        return buildResponse(NOT_FOUND, "Resource not found: " + e.getResourcePath());
    }

    @ExceptionHandler(OverLappingException.class)
    public ResponseEntity<ApiResponse<Object>> handleOverLappingException(OverLappingException e) {
        return buildResponse(CONFLICT, e.getMessage());
    }

    @ExceptionHandler(PriceNotFountException.class)
    public ResponseEntity<ApiResponse<Object>> handlePriceNotFountException(PriceNotFountException e) {
        return buildResponse(NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ScreenNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleScreenNotFoundException(ScreenNotFoundException e) {
        return buildResponse(NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(SeatNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleSeatNotFoundException(SeatNotFoundException e) {
       return buildResponse(NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ShowNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleShowNotFoundException(ShowNotFoundException e) {
        return buildResponse(NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ShowSeatNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleShowSeatNotFoundException(ShowSeatNotFoundException e) {
        return buildResponse(NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ShowSeatNotAvailableException.class)
    public ResponseEntity<ApiResponse<Object>> handleShowSeatNotAvailableException(ShowSeatNotAvailableException e) {
        return buildResponse(CONFLICT, e.getMessage());
    }

    @ExceptionHandler(TheatreNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleTheatreNotFoundException(TheatreNotFoundException e) {
        return buildResponse(NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserNotFoundException(UserNotFoundException e) {
        return buildResponse(NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(UnAuthorizedAccessException.class)
    public ResponseEntity<ApiResponse<Object>> handleUnAuthorizedAccessException(UnAuthorizedAccessException e) {
        return buildResponse(UNAUTHORIZED, e.getMessage());
    }

    //Stripe Exceptions
    @ExceptionHandler(StripeException.class)
    public ResponseEntity<ApiResponse<Object>> handleStripException(StripeException e) {
        return buildResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(SignatureVerificationException.class)
    public ResponseEntity<ApiResponse<Object>> handleSignatureVerificationException(SignatureVerificationException e) {
        logger.error("Error", e);
        return buildResponse(BAD_REQUEST, "Invalid signature");
    }


    // Generic Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception e) {

        HttpStatus status = INTERNAL_SERVER_ERROR;
        String message = "An unexpected error occurred.";
        Map<String, String> errors = null;

        Throwable cause = e.getCause() != null ? e.getCause() : e; // Get the root cause

        if (cause instanceof MethodArgumentNotValidException exception) {
            errors = exception.getBindingResult().getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "Invalid value",
                            (existing, replacement) -> existing

                    ));
            status = BAD_REQUEST;
            message = "Validation error";
        }
        else if (cause instanceof InvalidFormatException exception) {
            Class<?> targetType = exception.getTargetType();
            if (targetType.isEnum()) {
                List<String> allowedValues = Arrays.stream(targetType.getEnumConstants())
                        .map(Object::toString)
                        .toList();

                errors = new HashMap<>();
                errors.put("invalidValue", exception.getValue().toString());
                errors.put("allowedValues", allowedValues.toString());

                return buildResponse(BAD_REQUEST, "Invalid enum value", errors);
            }
        }
        else if (cause instanceof HttpMessageNotReadableException) {
            message = "Request body is missing.";
            status = HttpStatus.BAD_REQUEST;
        }
        else if (cause instanceof MissingServletRequestParameterException) {
            message = "Required query parameter is missing. Please provide the necessary parameter.";
            status = HttpStatus.BAD_REQUEST;
        }

        else if (cause instanceof JsonParseException) {
            message = "Invalid Json";
            status = BAD_REQUEST;
        }

        logger.error("Error: ", e);

        if (errors != null && !errors.isEmpty()) {
            return buildResponse(status, message, errors);
        }

        return buildResponse(status, message);
    }


    private ResponseEntity<ApiResponse<Object>> buildResponse(HttpStatus status, String message, Map<String, String> errors) {
        ApiResponse<Object> response = ResponseBuilder.error(status, message, errors);
        return ResponseEntity.status(status).body(response);
    }

    private ResponseEntity<ApiResponse<Object>> buildResponse(HttpStatus status, String message) {
        ApiResponse<Object> response = ResponseBuilder.error(status, message);
        return ResponseEntity.status(status).body(response);
    }
}
