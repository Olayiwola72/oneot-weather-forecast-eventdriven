package com.oneot.weather_forecast.query.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.oneot.weather_forecast.common.config.MessageSourceConfig;
import com.oneot.weather_forecast.common.dto.ErrorResponse;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;
    private ReloadableResourceBundleMessageSource messageSource;

    @BeforeEach
    void setUp() {
        MessageSourceConfig messageSourceConfig = new MessageSourceConfig();
        messageSource = messageSourceConfig.messageSource();
        exceptionHandler = new GlobalExceptionHandler(messageSource);
    }

    @Test
    void testHandleMethodArgumentNotValidException() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("objectName", "fieldName1", "Field1 error message");
        FieldError fieldError2 = new FieldError("objectName", "fieldName2", "Field2 error message");

        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));
        when(bindingResult.hasGlobalErrors()).thenReturn(true);

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleMethodArgumentNotValid(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).getErrors().size());

        // Assertions for specific field errors
        assertEquals("Field1 error message", response.getBody().getErrors().get(0).getErrorMessage());
        assertEquals("fieldName1", response.getBody().getErrors().get(0).getFieldName());

        assertEquals("Field2 error message", response.getBody().getErrors().get(1).getErrorMessage());
        assertEquals("fieldName2", response.getBody().getErrors().get(1).getFieldName());
    }

    @Test
    void testHandleMethodArgumentNotValidWithGlobalError() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of());
        when(bindingResult.hasGlobalErrors()).thenReturn(true);

        ObjectError globalError = new ObjectError("globalError", "Global error message");
        when(bindingResult.getGlobalErrors()).thenReturn(List.of(globalError));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleMethodArgumentNotValid(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(1, response.getBody().getErrors().size());

        // Assertions for global error
        assertEquals("Global error message", response.getBody().getErrors().get(0).getErrorMessage());
        assertEquals(null, response.getBody().getErrors().get(0).getFieldName()); // Global errors have null field name
    }

    @Test
    void testHandleMethodArgumentNotValidWithFallbackMessage() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of());
        when(bindingResult.hasGlobalErrors()).thenReturn(false);

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleMethodArgumentNotValid(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(1, response.getBody().getErrors().size());

        assertEquals(
            messageSource.getMessage("json.validation.failed", null, LocaleContextHolder.getLocale()),
            response.getBody().getErrors().get(0).getErrorMessage()
        );
        assertNull(response.getBody().getErrors().get(0).getFieldName());
    }

    @Test
    void testHandleConstraintViolationException() {
        // Mock ConstraintViolationException and its constraint violations
        ConstraintViolationException exception = mock(ConstraintViolationException.class);

        // Mock a set of constraint violations
        Set<ConstraintViolation<?>> constraintViolations = new HashSet<>();
        constraintViolations.add(mockConstraintViolation("Validation message 1", "field1"));
        constraintViolations.add(mockConstraintViolation("Validation message 2", "field2"));

        // Define behavior for the mocked exception
        when(exception.getConstraintViolations()).thenReturn(constraintViolations);

        // Invoke the handler method
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleConstraintViolationException(exception);

        // Assertions
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(2, response.getBody().getErrors().size());
    }

    // Helper method to mock a ConstraintViolation
    // Correct way to mock ConstraintViolation getPropertyPath() method
    private ConstraintViolation<?> mockConstraintViolation(String message, String fieldName) {
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn(message);
        // Mocking getPropertyPath() to return a Path mock
        Path pathMock = mock(Path.class);
        when(violation.getPropertyPath()).thenReturn(pathMock);
        when(pathMock.toString()).thenReturn(fieldName); // Mocking toString() of Path
        return violation;
    }


    @Test
    void testHandleHttpMessageNotReadableException() {
    	HttpInputMessage mockHttpInputMessage = mock(HttpInputMessage.class);

        MismatchedInputException mockException = mock(MismatchedInputException.class);
        when(mockException.getPathReference()).thenReturn("fieldName");
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException("Message not readable", mockException, mockHttpInputMessage);

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleHttpMessageNotReadable(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(1, response.getBody().getErrors().size());
    }


    @Test
    void testHandleHttpMessageNotReadable_FallBack() {
    	HttpInputMessage mockHttpInputMessage = mock(HttpInputMessage.class);
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException("Message not readable", mockHttpInputMessage);

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleHttpMessageNotReadable(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(1, response.getBody().getErrors().size());
        assertEquals(
            messageSource.getMessage("json.invalid.format", null, LocaleContextHolder.getLocale()),
            response.getBody().getErrors().get(0).getErrorMessage()
        );
        assertNull(response.getBody().getErrors().get(0).getFieldName());
    }

    @Test
    void testHandleObjectNotFoundException() {
        ObjectNotFoundException exception = mock(ObjectNotFoundException.class);

        // Mock behavior for the exception
        String entityName = "User";
        String identifier = "123";
        when(exception.getEntityName()).thenReturn(entityName);
        when(exception.getIdentifier()).thenReturn(identifier);


        // Assuming you need to get entityName and identifier for message source
        // For instance, if you want to get the message key in a specific format
        String errorMessage = messageSource.getMessage(
			"NotFound",
			new Object[]{
				entityName.substring(0, 1).toUpperCase() + entityName.substring(1),
				identifier.toString()
			},
			LocaleContextHolder.getLocale()
		);

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleObjectNotFoundException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(1, response.getBody().getErrors().size());
        assertEquals(errorMessage, response.getBody().getErrors().get(0).getErrorMessage());
        assertEquals("id", response.getBody().getErrors().get(0).getFieldName());
    }

    @Test
    void testHandleMismatchedInputException() {
        MismatchedInputException exception = mock(MismatchedInputException.class);
        String pathReference = "field[\"subfield\"]"; // Example path reference
        String errorMessage = "Mismatched input";

        when(exception.getPathReference()).thenReturn(pathReference);
        when(exception.getMessage()).thenReturn(errorMessage);

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleMismatchedInputException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(1, response.getBody().getErrors().size());
        assertEquals(
                messageSource.getMessage("json.invalid.value", new Object[]{ "subfield" }, LocaleContextHolder.getLocale()),
                response.getBody().getErrors().get(0).getErrorMessage()
        );
        assertEquals("subfield", response.getBody().getErrors().get(0).getFieldName());
    }

    @Test
    void testHandleNoResourceFoundException() {
        NoResourceFoundException exception = new NoResourceFoundException(null, "Resource not found");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleNoResourceFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(1, response.getBody().getErrors().size());
        assertNotNull(response.getBody().getErrors().get(0).getErrorMessage());
        assertNull(response.getBody().getErrors().get(0).getFieldName());
    }

    @Test
    void testHandleHttpRequestMethodNotSupportedException() {
        HttpRequestMethodNotSupportedException exception = new HttpRequestMethodNotSupportedException("Method not supported");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleHttpRequestMethodNotSupportedException(exception);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        assertEquals(1, response.getBody().getErrors().size());
        assertNotNull(response.getBody().getErrors().get(0).getErrorMessage());
        assertNull(response.getBody().getErrors().get(0).getFieldName());
    }

    @Test
    void testHandleMissingPathVariable() {
    	MissingPathVariableException exception = new MissingPathVariableException("Missing path variable", null);

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleMissingPathVariable(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(1, response.getBody().getErrors().size());
        assertNotNull(response.getBody().getErrors().get(0).getErrorMessage());
        assertNull(response.getBody().getErrors().get(0).getFieldName());
    }

    @Test
    void testHandleTypeMismatch() {
        // Mock the MethodArgumentTypeMismatchException
        MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);
        when(exception.getName()).thenReturn("id");
        when(exception.getValue()).thenReturn("stringValue");
        when(exception.getRequiredType()).thenAnswer(invocation -> String.class);

        // Call the method under test
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleTypeMismatch(exception);

        // Assertions
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(1, response.getBody().getErrors().size());
        assertNotNull(response.getBody().getErrors().get(0).getErrorMessage());
        assertEquals("id", response.getBody().getErrors().get(0).getFieldName());
    }

    @Test
    void testHandleIllegalArgumentException() {
    	IllegalArgumentException exception = new IllegalArgumentException("Illegal Argument");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleIllegalArgumentException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(1, response.getBody().getErrors().size());
        assertNotNull(response.getBody().getErrors().get(0).getErrorMessage());
    }

    @Test
    void testHandleMissingServletRequestParameterException() {
    	MissingServletRequestParameterException exception = new MissingServletRequestParameterException("token", "fieldName");
        ResponseEntity<ErrorResponse> response = exceptionHandler.handleMissingServletRequestParameterException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(1, response.getBody().getErrors().size());
        assertEquals(exception.getMessage(), response.getBody().getErrors().get(0).getErrorMessage());
        assertEquals(exception.getParameterName(), response.getBody().getErrors().get(0).getFieldName());
    }

    @Test
    void testHandleOtherExceptions() {
        Exception exception = new Exception("Internal server error");

        ResponseEntity<ErrorResponse> response = exceptionHandler.handleOtherExceptions(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(1, response.getBody().getErrors().size());
        assertEquals(
        	messageSource.getMessage("InternalServerError", null, LocaleContextHolder.getLocale()),
        	response.getBody().getErrors().get(0).getErrorMessage()
        );
        assertNull(response.getBody().getErrors().get(0).getFieldName());
    }
}
