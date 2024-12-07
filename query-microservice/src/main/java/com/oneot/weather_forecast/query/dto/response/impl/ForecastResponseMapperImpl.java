package com.oneot.weather_forecast.query.dto.response.impl;

import com.oneot.weather_forecast.query.model.QueryForecast;
import com.oneot.weather_forecast.query.dto.response.DayResponse;
import com.oneot.weather_forecast.query.dto.response.ForecastResponse;
import com.oneot.weather_forecast.query.dto.response.ForecastResponseMapper;
import com.oneot.weather_forecast.query.dto.response.NightResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ForecastResponseMapperImpl is the implementation of the ForecastResponseMapper interface.
 * It provides methods to convert QueryForecast entities into ForecastResponse DTOs.
 */
@Service
public class ForecastResponseMapperImpl implements ForecastResponseMapper {

    /**
     * Converts a QueryForecast model to a ForecastResponse DTO.
     *
     * @param queryForecast The QueryForecast model to be converted.
     * @return A ForecastResponse DTO representing the queryForecast data.
     */
    @Override
    public ForecastResponse toForecastResponse(QueryForecast queryForecast) {
        if(queryForecast == null) return null;

        return new ForecastResponse(
                queryForecast.getDate(), // Map the date from the QueryForecast model
                DayResponse.fromEntity(queryForecast.getDay()), // Convert Day model to DayResponse DTO
                NightResponse.fromEntity(queryForecast.getNight()) // Convert Night model to NightResponse DTO
        );
    }

    /**
     * Converts a list of QueryForecast entities to a list of ForecastResponse DTOs.
     *
     * @param queryForecasts A list of QueryForecast entities to be converted.
     * @return A list of ForecastResponse DTOs representing the forecast data.
     */
    @Override
    public List<ForecastResponse> toForecastResponseList(List<QueryForecast> queryForecasts) {
        return queryForecasts.stream() // Stream the list of QueryForecast entities
                .map(this::toForecastResponse) // Convert each QueryForecast model to ForecastResponse DTO
                .collect(Collectors.toList()); // Collect the results into a list
    }
}