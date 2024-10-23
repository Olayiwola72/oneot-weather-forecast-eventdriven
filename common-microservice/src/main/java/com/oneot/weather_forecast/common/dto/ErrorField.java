package com.oneot.weather_forecast.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an error response field in the API.
 * This class is used to provide detailed information about errors
 * that occur during API requests, including the error message
 * and the field name associated with the error.
 */
@Setter // Lombok annotation to generate setter methods
@Getter // Lombok annotation to generate getter methods
@AllArgsConstructor // Lombok annotation to generate a constructor with all fields
@Schema(description = "Error Response Field Information") // OpenAPI schema annotation for documentation
public class ErrorField {

    /**
     * The error message describing the issue.
     * This field is read-only and provides a user-friendly message
     * indicating what went wrong.
     */
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Error Message", example = "Field cannot be blank")
    private String errorMessage;

    /**
     * The name of the field that caused the error.
     * This field is read-only and helps identify which input field
     * is associated with the error message.
     */
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Field Name", example = "message")
    private String fieldName;

    /**
     * Constructor for creating an ErrorField with an error message.
     * This constructor allows the creation of an ErrorField instance
     * when only the error message is provided.
     *
     * @param errorMessage The error message to be set.
     */
    public ErrorField(String errorMessage) {
        this.setErrorMessage(errorMessage); // Set the error message using the setter
    }
}
