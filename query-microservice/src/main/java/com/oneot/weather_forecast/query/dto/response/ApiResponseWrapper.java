package com.oneot.weather_forecast.query.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A generic wrapper class for API responses.
 * 
 * @param <T> the type of the data contained in the response
 */
@Schema(description = "A generic wrapper class for API responses.")
public record ApiResponseWrapper<T>(
    @Schema(description = "The HTTP status code of the response.")
    int status, 
    
    @Schema(description = "A message providing additional information about the response.")
    String message, 
    
    @Schema(description = "The data contained in the response.")
    T data) {

    // Static factory method for successful responses
    /**
     * Creates a successful API response.
     * 
     * @param message the success message
     * @param data the data to be included in the response
     * @return an ApiResponseWrapper representing a successful response
     */
    public static <T> ApiResponseWrapper<T> success(String message, T data) {
        return new ApiResponseWrapper<>(200, message, data);
    }

    // Static factory method for custom responses
    /**
     * Creates a custom API response with a specified status.
     * 
     * @param status the HTTP status code
     * @param message the message to be included in the response
     * @param data the data to be included in the response
     * @return an ApiResponseWrapper representing a custom response
     */
    public static <T> ApiResponseWrapper<T> custom(int status, String message, T data) {
        return new ApiResponseWrapper<>(status, message, data);
    }

}