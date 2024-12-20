package com.oneot.weather_forecast.search.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Abstract class for common weather forecast attributes.
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class WeatherAttributesResponse {
    /**
     * The weather phenomenon (e.g., "Sunny", "Cloudy", "Rainy").
     */
    @Schema(description = "The weather phenomenon", example = "Clear", required = true)
    private String phenomenon;

    /**
     * The minimum temperature expected.
     */
    @Schema(description = "Minimum temperature expected", example = "-5")
    private Integer tempMin;

    /**
     * The maximum temperature expected.
     */
    @Schema(description = "Maximum temperature expected", example = "5")
    private Integer tempMax;


    public WeatherAttributesResponse(String phenomenon, Integer tempMin, Integer tempMax) {
        this.phenomenon = phenomenon;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
    }

}
