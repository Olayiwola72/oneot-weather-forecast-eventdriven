package com.oneot.weather_forecast.write.service;

import com.oneot.weather_forecast.common.repository.ForecastRepository;
import com.oneot.weather_forecast.write.dto.ForecastResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service responsible for fetching and saving weather forecasts.
 * 
 * This service interacts with an external weather API to retrieve
 * weather forecast data and saves it to a database using the
 * ForecastRepository from the common module.
 */
@Service
public class ForecastService {

    private static final Logger logger = LoggerFactory.getLogger(ForecastService.class);

    private final ForecastRepository forecastRepository;
    private final WeatherApiClient weatherApiClient;
    private final MessageSource messageSource;

    /**
     * Constructs a new ForecastService with the specified dependencies.
     *
     * @param forecastRepository Repository for storing weather forecasts
     * @param weatherApiClient Client for fetching weather data from the external API
     * @param messageSource Source for internationalized messages
     */
    public ForecastService(
            ForecastRepository forecastRepository,
            WeatherApiClient weatherApiClient,
            MessageSource messageSource
    ){
        this.forecastRepository = forecastRepository;
        this.weatherApiClient = weatherApiClient;
        this.messageSource = messageSource;
    }

    /**
     * Fetches the weather forecast from the external API.
     * 
     * This method attempts to retrieve the forecast data from the
     * weather API.
     *
     * @return Optional<ForecastResponse> containing the forecast data if successful, empty otherwise
     */
    private Optional<ForecastResponse> getWeatherForecast() {
        try {
            return Optional.of(weatherApiClient.fetchForecast());
        } catch (Exception exception) {
            logError(exception);
            return Optional.empty();
        }
    }

    /**
     * Scheduled method to periodically fetch and save weather forecasts.
     * 
     * This method runs at a fixed rate defined by the 'retry.interval' property,
     * with an initial delay defined by 'retry.initialDelay'.
     */
    @Scheduled(fixedRateString = "${retry.interval}", initialDelayString = "${retry.initialDelay}")
    public void saveWeatherForecasts(){
        forecastRepository.deleteAll();
        Optional<ForecastResponse> forecastResponseOptional = getWeatherForecast();
        forecastResponseOptional.ifPresent(forecastResponse -> forecastRepository.saveAll(forecastResponse.forecasts()));
    }

    /**
     * Utility method to log errors with internationalized messages.
     *
     * @param exception The exception to be logged
     */
    private void logError(Exception exception) {
        String errorMessage = messageSource.getMessage("error.fetch.weather.forecast", null, LocaleContextHolder.getLocale());
        logger.error(errorMessage, exception);
    }
    
}
