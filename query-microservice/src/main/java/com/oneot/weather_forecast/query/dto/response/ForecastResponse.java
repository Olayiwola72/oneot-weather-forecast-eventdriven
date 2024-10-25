package com.oneot.weather_forecast.query.dto.response;

import com.oneot.weather_forecast.common.model.Forecast;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
    private String date;

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
     * Constructs a ForecastResponse from the Forecast model.
     *
     * @param forecast The Forecast model to map.
     * @return A new instance of ForecastResponse populated with the Forecast model data.
     */
    public static ForecastResponse fromEntity(Forecast forecast) {
        return new ForecastResponse(
                forecast.getDate(),
                DayResponse.fromEntity(forecast.getDay()),
                NightResponse.fromEntity(forecast.getNight())
        );
    }
}

