package com.oneot.weather_forecast.query.dto.response.impl;

import com.oneot.weather_forecast.common.model.Forecast;
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
     * Converts a Forecast model to a ForecastResponse DTO.
     *
     * @param forecast The Forecast model to be converted.
     * @return A ForecastResponse DTO representing the forecast data.
     */
    @Override
    public ForecastResponse toForecastResponse(Forecast forecast) {
        if(forecast == null) return null;

        return new ForecastResponse(
                forecast.getDate(), // Map the date from the Forecast model
                DayResponse.fromEntity(forecast.getDay()), // Convert Day model to DayResponse DTO
                NightResponse.fromEntity(forecast.getNight()) // Convert Night model to NightResponse DTO
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
                .map(this::toForecastResponse) // Convert each Forecast model to ForecastResponse DTO
                .collect(Collectors.toList()); // Collect the results into a list
    }
}