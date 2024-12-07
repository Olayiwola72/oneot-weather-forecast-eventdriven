package com.oneot.weather_forecast.query.dto.response;

import com.oneot.weather_forecast.query.model.QueryForecast;

import java.util.List;

/**
 * ForecastResponseMapper is an interface that defines methods for mapping
 * QueryForecast entities to their corresponding response DTOs.
 */
public interface ForecastResponseMapper {

    /**
     * Converts a QueryForecast model to a ForecastResponse DTO.
     *
     * @param queryForecast The QueryForecast model to be converted.
     * @return A ForecastResponse DTO representing the queryForecast data.
     */
    ForecastResponse toForecastResponse(QueryForecast queryForecast);

    /**
     * Converts a list of QueryForecast entities to a list of ForecastResponse DTOs.
     *
     * @param queryForecasts A list of QueryForecast entities to be converted.
     * @return A list of ForecastResponse DTOs representing the forecast data.
     */
    List<ForecastResponse> toForecastResponseList(List<QueryForecast> queryForecasts);
}