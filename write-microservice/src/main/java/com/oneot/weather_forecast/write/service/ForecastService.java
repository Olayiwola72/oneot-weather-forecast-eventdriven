package com.oneot.weather_forecast.write.service;

import com.oneot.weather_forecast.common.model.Forecast;
import com.oneot.weather_forecast.common.repository.ForecastRepository;
import com.oneot.weather_forecast.write.dto.ForecastResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service responsible for fetching and saving weather forecasts.
 * This service interacts with an external weather API to retrieve
 * weather forecast data and saves it to a database using the
 * ForecastRepository.
 */
@Service
public class ForecastService {

    private final ForecastRepository forecastRepository; // Repository for storing weather forecasts
    private final WeatherApiClient weatherApiClient; // Client for fetching weather data from the external API

    /**
     * Constructs a new ForecastService with the specified dependencies.
     *
     * @param forecastRepository Repository for storing weather forecasts
     * @param weatherApiClient Client for fetching weather data from the external API
     */
    public ForecastService(ForecastRepository forecastRepository, WeatherApiClient weatherApiClient) {
        this.forecastRepository = forecastRepository; // Initialize the forecast repository
        this.weatherApiClient = weatherApiClient; // Initialize the weather API client
    }

    /**
     * Scheduled method to periodically fetch and save weather forecasts.
     * This method runs at a fixed rate defined by the 'retry.interval' property,
     * with an initial delay defined by 'retry.initialDelay'.
     */
    @Scheduled(fixedRateString = "${retry.interval}", initialDelayString = "${retry.initialDelay}")
    public void saveWeatherForecasts() throws Exception {
        Optional<ForecastResponse> forecastResponseOptional = getWeatherForecast(); // Get the forecast data

        // If forecast data is present, process and save it to the repository
        forecastResponseOptional.ifPresent(this::processForecastResponse);
    }

    /**
     * Fetches the weather forecast from the external API.
     * 
     * This method attempts to retrieve the forecast data from the
     * weather API and handles any exceptions that may occur.
     *
     * @return Optional<ForecastResponse> containing the forecast data if successful, empty otherwise
     */
    private Optional<ForecastResponse> getWeatherForecast() throws Exception {
        return Optional.of(weatherApiClient.fetchForecast()); // Fetch forecast data
    }

    /**
     * Processes the fetched forecast response and saves or updates the forecasts.
     *
     * @param forecastResponse The response containing the forecast data to be processed
     */
    private void processForecastResponse(ForecastResponse forecastResponse) {
        for (Forecast newForecast : forecastResponse.forecasts()) {
            saveOrUpdateForecast(newForecast); // Save or update each forecast
        }
    }
    
    /**
     * Saves a new forecast or updates an existing one based on its date.
     *
     * @param newForecast The new forecast data to be saved or updated
     */
    private void saveOrUpdateForecast(Forecast newForecast) {
        Optional<Forecast> existingForecastOptional = forecastRepository.findByDate(newForecast.getDate()); // Check for existing forecast
        if (existingForecastOptional.isPresent()) {
            updateWeatherForecast(existingForecastOptional.get(), newForecast); // Update existing forecast
        } else {
            forecastRepository.save(newForecast); // Save new forecast
        }
    }

    /**
     * Updates an existing weather forecast with new data.
     *
     * @param existingForecast The existing forecast to be updated
     * @param newForecast The new forecast data to update with
     */
    private void updateWeatherForecast(Forecast existingForecast, Forecast newForecast) {
        existingForecast.setDay(newForecast.getDay()); // Update day data
        existingForecast.setNight(newForecast.getNight()); // Update night data
        forecastRepository.save(existingForecast); // Save the updated forecast
    }
}
