package com.oneot.weather_forecast.query.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * This class represents a request for weather forecast data.
 * It contains the location for which the forecast is requested.
 */
public record ForecastRequest(
        @NotBlank // Ensures that the place field is not blank
        @Schema(accessMode = Schema.AccessMode.READ_WRITE, description = "forecast's location", example = "Tarlinn") // Swagger schema annotation for API documentation
        String place // The location for which the forecast is requested
) {

    /**
     * Factory method to create a ForecastRequest instance.
     *
     * @param place The location for which the forecast is requested.
     * @return A new instance of ForecastRequest.
     */
    @JsonCreator
    public static ForecastRequest create(
            @JsonProperty("place") String place // JSON property for deserialization
    ) {
        return new ForecastRequest(place); // Returns a new instance of ForecastRequest
    }
    
}