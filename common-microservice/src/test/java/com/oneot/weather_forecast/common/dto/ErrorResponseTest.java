package com.oneot.weather_forecast.common.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorResponseTest {

    private ErrorResponse errorResponse;

    @BeforeEach
    public void setUp() {
        // Initialize a new ErrorResponse object before each test
        errorResponse = new ErrorResponse();
    }

    @Test
    public void testDefaultConstructor() {
        // Check that the errors list is initialized as empty
        assertThat(errorResponse.getErrors()).isNotNull(); // Ensure the list is not null
        assertThat(errorResponse.getErrors()).isEmpty(); // Ensure the list is empty
    }

    @Test
    public void testConstructorWithSingleErrorMessage() {
        // Given
        String errorMessage = "Field cannot be blank";

        // When
        errorResponse = new ErrorResponse(errorMessage);

        // Then
        assertThat(errorResponse.getErrors()).hasSize(1); // Ensure there is one error
        assertThat(errorResponse.getErrors().get(0).getErrorMessage()).isEqualTo(errorMessage); // Check the error message
        assertThat(errorResponse.getErrors().get(0).getFieldName()).isNull(); // Ensure fieldName is null
    }

    @Test
    public void testConstructorWithErrorMessageAndFieldName() {
        // Given
        String errorMessage = "Field cannot be blank";
        String fieldName = "username";

        // When
        errorResponse = new ErrorResponse(errorMessage, fieldName);

        // Then
        assertThat(errorResponse.getErrors()).hasSize(1); // Ensure there is one error
        assertThat(errorResponse.getErrors().get(0).getErrorMessage()).isEqualTo(errorMessage); // Check the error message
        assertThat(errorResponse.getErrors().get(0).getFieldName()).isEqualTo(fieldName); // Check the field name
    }

    @Test
    public void testAddErrors() {
        // Given
        String errorMessage = "Field cannot be blank";
        ErrorField errorField = new ErrorField(errorMessage);

        // When
        errorResponse.addErrors(errorField);

        // Then
        assertThat(errorResponse.getErrors()).hasSize(1); // Ensure there is one error
        assertThat(errorResponse.getErrors().get(0).getErrorMessage()).isEqualTo(errorMessage); // Check the error message
    }
}
