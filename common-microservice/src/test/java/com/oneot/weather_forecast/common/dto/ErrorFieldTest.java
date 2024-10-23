package com.oneot.weather_forecast.common.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorFieldTest {

    @Test
    void testErrorFieldConstructorWithMessage() {
        // Given
        String errorMessage = "Field cannot be blank";

        // When
        ErrorField errorField = new ErrorField(errorMessage);

        // Then
        assertThat(errorField.getErrorMessage()).isEqualTo(errorMessage); // Check that the error message is set correctly
        assertThat(errorField.getFieldName()).isNull(); // Check that fieldName is null by default
    }

    @Test
    void testErrorFieldConstructorWithAllFields() {
        // Given
        String errorMessage = "Field cannot be blank";
        String fieldName = "message";

        // When
        ErrorField errorField = new ErrorField(errorMessage, fieldName);

        // Then
        assertThat(errorField.getErrorMessage()).isEqualTo(errorMessage); // Check that the error message is set correctly
        assertThat(errorField.getFieldName()).isEqualTo(fieldName); // Check that the field name is set correctly
    }

    @Test
    void testSetters() {
        // Given
        ErrorField errorField = new ErrorField("Initial error message");

        // When
        errorField.setFieldName("message");

        // Then
        assertThat(errorField.getFieldName()).isEqualTo("message"); // Check that the field name is set correctly
    }
}
