package com.oneot.weather_forecast.write.service;

import com.oneot.weather_forecast.write.dto.ForecastResponse;

/**
 * Interface for a Weather API client.
 * 
 * This interface defines the contract for fetching weather forecast data
 * from an external weather API. Implementations of this interface should
 * provide the specific logic for making HTTP requests to the API and
 * handling the response.
 */
public interface WeatherApiClient {
    
    /**
     * Fetches the weather forecast from the external API.
     *
     * @return ForecastResponse containing the weather forecast data
     * @throws Exception if an error occurs while fetching the forecast
     */
    ForecastResponse fetchForecast() throws Exception;

    /**
     * Retrieves the API URL used for making requests.
     *
     * @return A string representing the API URL
     */
    String getApiUrl();
}
