package com.oneot.weather_forecast.query.service;

import com.oneot.weather_forecast.common.entity.Forecast;
import com.oneot.weather_forecast.common.repository.ForecastRepository;
import com.oneot.weather_forecast.query.dto.response.ForecastResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing weather forecasts.
 * This class provides methods to retrieve weather forecasts
 * based on different criteria such as location and date.
 */
@Service // Indicates that this class is a service component in the Spring context
public class ForecastService {

    private final ForecastRepository forecastRepository; // Repository for accessing forecast data

    /**
     * Constructor for ForecastService.
     *
     * @param forecastRepository The repository used to access forecast data.
     */
    public ForecastService(ForecastRepository forecastRepository) {
        this.forecastRepository = forecastRepository; // Initialize the repository with constructor injection
    }

    /**
     * Retrieve all forecasts for the given place.
     *
     * @param place The location to search forecasts for.
     * @return A list of forecasts matching the place.
     */
    public List<Forecast> getAllForecastsByPlace(String place) {
        return forecastRepository.findAllByPlace(place); // Fetch forecasts from the repository
    }

    /**
     * Retrieve all forecasts for the current day.
     *
     * @return A list of today's forecasts for all locations.
     */
    public Optional<Forecast> getTodayForecast() {
        String today = LocalDate.now().toString(); // Get the current date in yyyy-MM-dd format
        return forecastRepository.findByDate(today);
    }

}