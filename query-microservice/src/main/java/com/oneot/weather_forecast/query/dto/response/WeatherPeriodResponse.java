package com.oneot.weather_forecast.query.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Abstract class for common weather forecast attributes.
 */
@Getter
@Setter
@NoArgsConstructor
public abstract class WeatherPeriodResponse extends WeatherAttributesResponse {
    /**
     * Additional textual description of the weather.
     */
    @Schema(description = "Additional textual description of the weather", example = "Clear skies with a slight breeze.")
    private String text;

    /**
     * List of places for which the forecast applies.
     */
    @Schema(description = "List of places where the forecast is applicable")
    private List<PlaceResponse> places;

    public WeatherPeriodResponse(String phenomenon, Integer tempMin, Integer tempMax, String text, List<PlaceResponse> places) {
        super(phenomenon, tempMin, tempMax);
        this.text = text;
        this.places = places;
    }

}
