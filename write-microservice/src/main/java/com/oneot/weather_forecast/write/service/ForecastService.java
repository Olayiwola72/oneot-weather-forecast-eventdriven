package com.oneot.weather_forecast.write.service;

import com.oneot.weather_forecast.common.enums.EventType;
import com.oneot.weather_forecast.common.event.ForecastEvent;
import com.oneot.weather_forecast.common.model.Forecast;
import com.oneot.weather_forecast.write.producer.ForecastEventProducer;
import com.oneot.weather_forecast.write.repository.ForecastRepository;
import com.oneot.weather_forecast.write.dto.ForecastResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service responsible for fetching, processing, and saving weather forecasts.
 * This service interacts with an external weather API to retrieve weather forecast data,
 * saves it to a database using the ForecastRepository, and dispatches the forecasts as events.
 */
@Service
public class ForecastService {

    private final ForecastRepository forecastRepository; // Repository for storing weather forecasts
    private final WeatherApiClient weatherApiClient; // Client for fetching weather data from the external API
    private final ForecastEventProducer forecastEventProducer; // Producer for sending forecast events to Kafka

    /**
     * Constructs a new ForecastService with the specified dependencies.
     *
     * @param forecastRepository Repository for storing weather forecasts
     * @param weatherApiClient Client for fetching weather data from the external API
     * @param forecastEventProducer Producer for sending forecast events to Kafka
     */
    public ForecastService(ForecastRepository forecastRepository, WeatherApiClient weatherApiClient, ForecastEventProducer forecastEventProducer) {
        this.forecastRepository = forecastRepository; // Initialize the forecast repository
        this.weatherApiClient = weatherApiClient; // Initialize the weather API client
        this.forecastEventProducer = forecastEventProducer; // Initialize the event producer
    }

    /**
     * Scheduled method to periodically fetch and save weather forecasts.
     * This method runs at a fixed rate defined by the 'retry.interval' property,
     * with an initial delay defined by 'retry.initialDelay'.
     * It retrieves the forecast data and processes it for saving and dispatching.
     */
    @Scheduled(fixedRateString = "${retry.interval}", initialDelayString = "${retry.initialDelay}")
    public void saveWeatherForecasts() throws Exception {
        Optional<ForecastResponse> forecastResponseOptional = getWeatherForecast(); // Get the forecast data

        // If forecast data is present, process and save it to the repository
        forecastResponseOptional.ifPresent(this::processForecastResponse);
    }

    /**
     * Fetches the weather forecast from the external API.
     * This method attempts to retrieve the forecast data from the weather API
     * and handles any exceptions that may occur.
     *
     * @return Optional<ForecastResponse> containing the forecast data if successful, empty otherwise
     */
    private Optional<ForecastResponse> getWeatherForecast() throws Exception {
        return Optional.of(weatherApiClient.fetchForecast()); // Fetch forecast data
    }

    /**
     * Processes the fetched forecast response and saves or updates the forecasts.
     * It also dispatches each forecast to Kafka for further processing.
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
     * If an existing forecast is found, it is updated; otherwise, a new forecast is saved.
     *
     * @param newForecast The new forecast data to be saved or updated
     */
    private void saveOrUpdateForecast(Forecast newForecast) {
        EventType eventType;
        Optional<Forecast> existingForecastOptional = forecastRepository.findByDate(newForecast.getDate()); // Check for existing forecast

        if(existingForecastOptional.isPresent()){
            eventType = EventType.FORECAST_UPDATED;
            newForecast = updateWeatherForecast(existingForecastOptional.get(), newForecast);
        }else{
            eventType = EventType.FORECAST_CREATED;
            newForecast = forecastRepository.save(newForecast);
        }

        sendEventsToTopic(new ForecastEvent(eventType, newForecast)); // Dispatch the forecast to Kafka
    }

    /**
     * Updates an existing weather forecast with new data.
     * This method modifies the existing forecast's day and night data
     * and saves the updated forecast back to the repository.
     *
     * @param existingForecast The existing forecast to be updated
     * @param newForecast The new forecast data to update with
     * @return The updated forecast
     */
    private Forecast updateWeatherForecast(Forecast existingForecast, Forecast newForecast) {
        existingForecast.setDay(newForecast.getDay()); // Update day data
        existingForecast.setNight(newForecast.getNight()); // Update night data
        return forecastRepository.save(existingForecast); // Save the updated forecast
    }

    /**
     * Sends the forecast to a Kafka topic for further processing.
     *
     * @param forecastEvent The forecast event data to be sent to Kafka
     */
    private void sendEventsToTopic(ForecastEvent forecastEvent) {
        forecastEventProducer.sendForecastEvent(forecastEvent); // Dispatch the forecast to Kafka
    }
}