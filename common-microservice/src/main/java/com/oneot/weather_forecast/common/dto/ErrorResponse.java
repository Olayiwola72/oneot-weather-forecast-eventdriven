package com.oneot.weather_forecast.common.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * Represents an error response in the API.
 * This class is used to encapsulate error information that is returned
 * to the client when an error occurs during API processing.
 */
@Schema(description = "Error Response Information") // OpenAPI schema annotation for documentation
@Getter // Lombok annotation to generate getter methods
public class ErrorResponse {

    /**
     * A list of error fields that provide detailed information about each error.
     * This field is required and cannot be null.
     */
    @Schema(description = "List of error fields", required = true) // OpenAPI schema annotation for documentation
    @NotNull // Validation annotation to ensure this field is not null
    private List<ErrorField> errors; // List of error details

    /**
     * Default constructor for ErrorResponse.
     * Initializes the errors list to an empty ArrayList.
     */
    public ErrorResponse() {
        this.errors = new ArrayList<>(); // Initialize the errors list
    }

    /**
     * Constructor for ErrorResponse that takes a single error message.
     * This constructor allows the creation of an ErrorResponse instance
     * with a specific error message.
     *
     * @param errorMessage The error message to be added to the errors list.
     */
    public ErrorResponse(String errorMessage) {
        this(); // Call the default constructor to initialize the errors list
        ErrorField errorField = new ErrorField(errorMessage); // Create an ErrorField with the provided message
        this.addErrors(errorField); // Add the error field to the errors list
    }

    /**
     * Constructor for ErrorResponse that takes an error message and a field name.
     * This constructor allows the creation of an ErrorResponse instance
     * with a specific error message and the name of the field associated with the error.
     *
     * @param errorMessage The error message to be added to the errors list.
     * @param fieldName The name of the field associated with the error.
     */
    public ErrorResponse(String errorMessage, String fieldName) {
        this(); // Call the default constructor to initialize the errors list
        ErrorField errorField = new ErrorField(errorMessage, fieldName); // Create an ErrorField with the provided message and field name
        this.addErrors(errorField); // Add the error field
    }

    public void addErrors(ErrorField errorField) {
        this.errors.add(errorField);
    }
}
