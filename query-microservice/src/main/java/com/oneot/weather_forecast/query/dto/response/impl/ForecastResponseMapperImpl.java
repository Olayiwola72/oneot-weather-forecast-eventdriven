package com.oneot.weather_forecast.query.dto.response.impl;

import com.oneot.weather_forecast.common.entity.Forecast;
import com.oneot.weather_forecast.query.dto.response.DayResponse;
import com.oneot.weather_forecast.query.dto.response.ForecastResponse;
import com.oneot.weather_forecast.query.dto.response.ForecastResponseMapper;
import com.oneot.weather_forecast.query.dto.response.NightResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ForecastResponseMapperImpl is the implementation of the ForecastResponseMapper interface.
 * It provides methods to convert Forecast entities into ForecastResponse DTOs.
 */
@Service
public class ForecastResponseMapperImpl implements ForecastResponseMapper {

    /**
     * Converts a Forecast entity to a ForecastResponse DTO.
     *
     * @param forecast The Forecast entity to be converted.
     * @return A ForecastResponse DTO representing the forecast data.
     */
    @Override
    public ForecastResponse toForecastResponse(Forecast forecast) {
        return new ForecastResponse(
                forecast.getDate(), // Map the date from the Forecast entity
                DayResponse.fromEntity(forecast.getDay()), // Convert Day entity to DayResponse DTO
                NightResponse.fromEntity(forecast.getNight()) // Convert Night entity to NightResponse DTO
        );
    }

    /**
     * Converts a list of Forecast entities to a list of ForecastResponse DTOs.
     *
     * @param forecasts A list of Forecast entities to be converted.
     * @return A list of ForecastResponse DTOs representing the forecast data.
     */
    @Override
    public List<ForecastResponse> toForecastResponseList(List<Forecast> forecasts) {
        return forecasts.stream() // Stream the list of Forecast entities
                .map(this::toForecastResponse) // Convert each Forecast entity to ForecastResponse DTO
                .collect(Collectors.toList()); // Collect the results into a list
    }
}