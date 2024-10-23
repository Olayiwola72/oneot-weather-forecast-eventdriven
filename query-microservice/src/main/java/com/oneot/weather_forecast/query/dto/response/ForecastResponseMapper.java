package com.oneot.weather_forecast.query.dto.response;

import com.oneot.weather_forecast.common.entity.Forecast;

import java.util.List;

/**
 * ForecastResponseMapper is an interface that defines methods for mapping
 * Forecast entities to their corresponding response DTOs.
 */
public interface ForecastResponseMapper {

    /**
     * Converts a Forecast entity to a ForecastResponse DTO.
     *
     * @param forecast The Forecast entity to be converted.
     * @return A ForecastResponse DTO representing the forecast data.
     */
    ForecastResponse toForecastResponse(Forecast forecast);

    /**
     * Converts a list of Forecast entities to a list of ForecastResponse DTOs.
     *
     * @param forecasts A list of Forecast entities to be converted.
     * @return A list of ForecastResponse DTOs representing the forecast data.
     */
    List<ForecastResponse> toForecastResponseList(List<Forecast> forecasts);
}