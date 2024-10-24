package com.oneot.weather_forecast.common.repository;

import com.oneot.weather_forecast.common.entity.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Forecast entities.
 * This interface provides CRUD operations for Forecast objects and allows for custom query methods.
 * It extends JpaRepository, which provides a full set of JPA repository functionality.
 */
public interface ForecastRepository extends JpaRepository<Forecast, Long> {

    /**
     * Find all forecasts for a given location.
     * The location is matched against the places list in the WeatherPeriod (day or night).
     *
     * @param place The Place to search for.
     * @return A list of forecasts matching the place.
     */
    @Query("SELECT f FROM Forecast f " +
            "JOIN f.day.places p_day " +
            "JOIN f.night.places p_night " +
            "WHERE p_day.name = :place OR p_night.name = :place")
    List<Forecast> findAllByPlace(@Param("place") String place);


    /**
     * Finds a forecast for a specific date.
     *
     * This method retrieves a forecast entity that matches the provided date.
     * If no forecast exists for the given date, an empty Optional is returned.
     *
     * @param currentDate The date for which the forecast is to be retrieved, 
     *                    formatted as yyyy-MM-dd.
     * @return An Optional containing the matching Forecast entity if found, 
     *         or an empty Optional if no forecast exists for the specified date.
     */
    Optional<Forecast> findByDate(String currentDate);
    
}

