package com.oneot.weather_forecast.query.exception;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Objects;

import com.oneot.weather_forecast.common.dto.ErrorField;
import com.oneot.weather_forecast.common.dto.ErrorResponse;
import jakarta.validation.Path;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Profile;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

/**
 * Global exception handler for the application.
 * This class handles various exceptions thrown by the application
 * and returns appropriate error responses to the client.
 * It uses Spring's @RestControllerAdvice to handle exceptions globally
 * across all controllers.
 */
@RestControllerAdvice // Indicates that this class provides global exception handling for REST controllers
public class GlobalExceptionHandler {
	private final ReloadableResourceBundleMessageSource messageSource; // Message source for internationalization

	/**
	 * Constructor for GlobalExceptionHandler.
	 * Initializes the message source for retrieving localized error messages.
	 *
	 * @param messageSource The message source to be used for error messages.
	 */
	public GlobalExceptionHandler(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource; // Set the message source
	}

	/**
	 * Handles MethodArgumentNotValidException.
	 * This exception is thrown when a method argument fails validation.
	 * It constructs an ErrorResponse containing details about the validation errors.
	 *
	 * @param ex The MethodArgumentNotValidException that was thrown.
	 * @return ResponseEntity containing the ErrorResponse and HTTP status 400 (BAD_REQUEST).
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();

		List<FieldError> fieldErrors = result.getFieldErrors();
		if(!fieldErrors.isEmpty()) {
			ErrorResponse errorResponse = new ErrorResponse();

			 for(FieldError fieldError : fieldErrors) {
				String fieldName = fieldError.getField();
				String errorCode = fieldError.getCode();
				String errorMessage = fieldError.getDefaultMessage();
				Object rejectedValue = fieldError.getRejectedValue();
				if(rejectedValue != null) rejectedValue = rejectedValue.toString();

				if(errorCode != null){
					try{
						errorMessage = messageSource.getMessage(
							errorCode,
							new Object[]{
							   fieldName,
							   rejectedValue
						   },
							LocaleContextHolder.getLocale()
						);
				   }catch(NoSuchMessageException ignored) {}
				}
				
				ErrorField errorField = new ErrorField(errorMessage, fieldName);
				errorResponse.addErrors(errorField);
			}

			return ResponseEntity.badRequest().body(errorResponse);
		}else if(result.hasGlobalErrors()){
				ErrorResponse errorResponse = new ErrorResponse();

			    for (ObjectError error : result.getGlobalErrors()) {
			        ErrorField errorField = new ErrorField(error.getDefaultMessage(), null);
					errorResponse.addErrors(errorField);
			    }

			    return ResponseEntity.badRequest().body(errorResponse);
		}else {
			ErrorResponse errorResponse = new ErrorResponse(messageSource.getMessage("json.validation.failed", null, LocaleContextHolder.getLocale()));
	        return ResponseEntity.badRequest().body(errorResponse);
		}
    }

	/**
	 * Handles ConstraintViolationException.
	 * This exception is thrown when a constraint violation occurs during validation.
	 * It constructs an ErrorResponse containing details about the constraint violations.
	 *
	 * @param ex The ConstraintViolationException that was thrown.
	 * @return ResponseEntity containing the ErrorResponse and HTTP status 400 (BAD_REQUEST).
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
		ErrorResponse errorResponse = new ErrorResponse();

		for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
			// Extract field name dynamically
            String fieldName = getLastFieldName(constraintViolation.getPropertyPath());

			// Dynamically get the validation code (e.g., NotBlank) from the constraint
			String validationCode = getValidationCode(constraintViolation);

			// Try to resolve a custom message for this specific field
			String errorMessage = messageSource.getMessage(
					validationCode,
					new Object[] { fieldName },
					constraintViolation.getMessage(), // Use getMessage() as fallback if no message is found
					LocaleContextHolder.getLocale()
			);

			ErrorField errorField = new ErrorField(errorMessage, fieldName);
			errorResponse.addErrors(errorField);
		}

    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	/**
	 * Handles HttpMessageNotReadableException.
	 * This exception is thrown when the request body cannot be read or parsed.
	 * It constructs an ErrorResponse with a relevant error message.
	 *
	 * @param ex The HttpMessageNotReadableException that was thrown.
	 * @return ResponseEntity containing the ErrorResponse and HTTP status 400 (BAD_REQUEST).
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
		Throwable cause = ex.getCause();

        if (cause instanceof MismatchedInputException mismatchedInputException) {
            return handleMismatchedInputException(mismatchedInputException);
        }

        if (cause instanceof ValueInstantiationException) {
        	ErrorResponse errorResponse = new ErrorResponse(ex.getMostSpecificCause().getMessage());
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // JsonParseException
		ErrorResponse errorResponse = new ErrorResponse(messageSource.getMessage("json.invalid.format", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

	/**
	 * Handles MismatchedInputException.
	 * This exception is thrown when the input does not match the expected type.
	 * It constructs an ErrorResponse with a relevant error message.
	 *
	 * @param ex The MismatchedInputException that was thrown.
	 * @return ResponseEntity containing the ErrorResponse and HTTP status 400 (BAD_REQUEST).
	 */
	@ExceptionHandler(MismatchedInputException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMismatchedInputException(MismatchedInputException ex) {
		String pathReference = ex.getPathReference(); // Get the path reference
        String fieldName = extractFieldName(pathReference); // Extract the field name
        String errorMessage = ex.getMessage();

        if(fieldName != null) {
        	errorMessage = messageSource.getMessage(
					"json.invalid.value",
					new Object[]{
							fieldName
						},
					LocaleContextHolder.getLocale()
			);
        }

        ErrorResponse errorResponse = new ErrorResponse(errorMessage, fieldName);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

	/**
	 * Handles NoResourceFoundException.
	 * This exception is thrown when a requested resource is not found.
	 * It constructs an ErrorResponse with a relevant error message.
	 *
	 * @param ex The NoResourceFoundException that was thrown.
	 * @return ResponseEntity containing the ErrorResponse and HTTP status 404 (NOT_FOUND).
	 */
	@ExceptionHandler(NoResourceFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
			ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}

	/**
	 * Handles HttpRequestMethodNotSupportedException.
	 * This exception is thrown when an HTTP method is not supported for a given endpoint.
	 * It constructs an ErrorResponse with a relevant error message.
	 *
	 * @param ex The HttpRequestMethodNotSupportedException that was thrown.
	 * @return ResponseEntity containing the ErrorResponse and HTTP status 405 (METHOD_NOT_ALLOWED).
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
	}

	/**
	 * Handles MissingPathVariableException.
	 * This exception is thrown when a required path variable is missing.
	 * It constructs an ErrorResponse with a relevant error message.
	 *
	 * @param ex The MissingPathVariableException that was thrown.
	 * @return ResponseEntity containing the ErrorResponse and HTTP status 400 (BAD_REQUEST).
	 */
	@ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorResponse> handleMissingPathVariable(MissingPathVariableException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Path parameter is missing: " + ex.getVariableName());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

	/**
	 * Handles MethodArgumentTypeMismatchException.
	 * This exception is thrown when a method argument is of the wrong type.
	 * It constructs an ErrorResponse with a relevant error message.
	 *
	 * @param ex The MethodArgumentTypeMismatchException that was thrown.
	 * @return ResponseEntity containing the ErrorResponse and HTTP status 400 (BAD_REQUEST).
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		String fieldName = ex.getName();

        String errorMessage = String.format("Invalid path variable '%s'. Expected type '%s'. Please check the request URL",
        		fieldName, Objects.requireNonNull(ex.getRequiredType()).getSimpleName());

        ErrorResponse errorResponse = new ErrorResponse(errorMessage, fieldName);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

	/**
	 * Handles IllegalArgumentException.
	 * This exception is thrown when an illegal argument is passed to a method.
	 * It constructs an ErrorResponse with a relevant error message.
	 *
	 * @param ex The IllegalArgumentException that was thrown.
	 * @return ResponseEntity containing the ErrorResponse and HTTP status 400 (BAD_REQUEST).
	 */
	@ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

	/**
	 * Handles MissingServletRequestParameterException.
	 * This exception is thrown when a required request parameter is missing.
	 * It constructs an ErrorResponse with a relevant error message.
	 *
	 * @param ex The MissingServletRequestParameterException that was thrown.
	 * @return ResponseEntity containing the ErrorResponse and HTTP status 400 (BAD_REQUEST).
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getParameterName());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

	/**
	 * Handles all other exceptions in production.
	 * This method is only active in the 'prod' profile and handles generic exceptions.
	 * It constructs an ErrorResponse with a generic error message.
	 *
	 * @param ex The Exception that was thrown.
	 * @return ResponseEntity containing the ErrorResponse and HTTP status 500 (INTERNAL_SERVER_ERROR).
	 */
	@Profile("prod")
	@ExceptionHandler(Exception.class)
   	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse(messageSource.getMessage("InternalServerError", null, LocaleContextHolder.getLocale()));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}

	/**
	 * Extracts the field name from a path reference.
	 * This method is used to parse the field name from the path reference string.
	 *
	 * @param pathReference The path reference string.
	 * @return The extracted field name, or null if not found.
	 */
	private String extractFieldName(String pathReference) {
        int startIndex = pathReference.indexOf("[\"");
        int endIndex = pathReference.indexOf("\"]");
        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            return pathReference.substring(startIndex + 2, endIndex);
        }
        return null;
    }

	// Helper method to extract the last node (field name) from the property path
	private String getLastFieldName(Path propertyPath) {
		if (propertyPath == null) return null;

		String fieldName = null;
		for (Path.Node node : propertyPath) {
			fieldName = node.getName(); // Get the last segment (field name)
		}

		return fieldName;
	}

	// Helper method to get the validation code from the ConstraintViolation
	private String getValidationCode(ConstraintViolation<?> violation) {
		// Get the annotation class for the constraint (e.g., NotBlank, Size)
		Annotation annotation = violation.getConstraintDescriptor().getAnnotation();

		// Return the simple name of the annotation class as the validation code
		return annotation.annotationType().getSimpleName();
	}
}
