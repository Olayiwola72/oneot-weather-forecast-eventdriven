package com.oneot.weather_forecast.search.dto.response;

import com.oneot.weather_forecast.search.model.SearchForecast;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for the weather forecast response.
 * This class represents the weather forecast for a specific date,
 * including both day and night forecasts.
 */
@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Represents a weather forecast for a specific date, including day and night details.")
public class ForecastResponse {

    /**
     * The date of the forecast.
     */
    @Schema(description = "The date of the forecast", example = "2024-10-23", required = true)
    private LocalDate date;

    /**
     * The daytime weather forecast.
     */
    @Schema(description = "Daytime weather forecast details")
    private DayResponse day;

    /**
     * The nighttime weather forecast.
     */
    @Schema(description = "Nighttime weather forecast details")
    private NightResponse night;

    /**
     * Constructs a ForecastResponse from the SearchForecast model.
     *
     * @param searchForecast The SearchForecast model to map.
     * @return A new instance of ForecastResponse populated with the SearchForecast model data.
     */
    public static ForecastResponse fromEntity(SearchForecast searchForecast) {
        return new ForecastResponse(
                searchForecast.getDate(),
                DayResponse.fromEntity(searchForecast.getDay()),
                NightResponse.fromEntity(searchForecast.getNight())
        );
    }
}

