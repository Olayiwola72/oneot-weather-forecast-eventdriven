package com.oneot.weather_forecast.search.dto.response;

import com.oneot.weather_forecast.search.model.SearchForecast;

import java.util.List;

/**
 * ForecastResponseMapper is an interface that defines methods for mapping
 * SearchForecast entities to their corresponding response DTOs.
 */
public interface ForecastResponseMapper {

    /**
     * Converts a SearchForecast model to a ForecastResponse DTO.
     *
     * @param searchForecast The SearchForecast model to be converted.
     * @return A ForecastResponse DTO representing the searchForecast data.
     */
    ForecastResponse toForecastResponse(SearchForecast searchForecast);

    /**
     * Converts a list of SearchForecast entities to a list of ForecastResponse DTOs.
     *
     * @param searchForecasts A list of SearchForecast entities to be converted.
     * @return A list of ForecastResponse DTOs representing the forecast data.
     */
    List<ForecastResponse> toForecastResponseList(List<SearchForecast> searchForecasts);
}