package com.oneot.weather_forecast.search.dto.response.impl;

import com.oneot.weather_forecast.search.dto.response.DayResponse;
import com.oneot.weather_forecast.search.dto.response.ForecastResponse;
import com.oneot.weather_forecast.search.dto.response.ForecastResponseMapper;
import com.oneot.weather_forecast.search.dto.response.NightResponse;
import com.oneot.weather_forecast.search.model.SearchForecast;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ForecastResponseMapperImpl is the implementation of the ForecastResponseMapper interface.
 * It provides methods to convert SearchForecast entities into ForecastResponse DTOs.
 */
@Service
public class ForecastResponseMapperImpl implements ForecastResponseMapper {

    /**
     * Converts a SearchForecast model to a ForecastResponse DTO.
     *
     * @param searchForecast The SearchForecast model to be converted.
     * @return A ForecastResponse DTO representing the searchForecast data.
     */
    @Override
    public ForecastResponse toForecastResponse(SearchForecast searchForecast) {
        if(searchForecast == null) return null;

        return new ForecastResponse(
                searchForecast.getDate(), // Map the date from the SearchForecast model
                DayResponse.fromEntity(searchForecast.getDay()), // Convert Day model to DayResponse DTO
                NightResponse.fromEntity(searchForecast.getNight()) // Convert Night model to NightResponse DTO
        );
    }

    /**
     * Converts a list of SearchForecast entities to a list of ForecastResponse DTOs.
     *
     * @param searchForecasts A list of SearchForecast entities to be converted.
     * @return A list of ForecastResponse DTOs representing the forecast data.
     */
    @Override
    public List<ForecastResponse> toForecastResponseList(List<SearchForecast> searchForecasts) {
        return searchForecasts.stream() // Stream the list of SearchForecast entities
                .map(this::toForecastResponse) // Convert each SearchForecast model to ForecastResponse DTO
                .collect(Collectors.toList()); // Collect the results into a list
    }
}