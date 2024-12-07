package com.oneot.weather_forecast.write.repository;

import com.oneot.weather_forecast.common.model.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Forecast entities.
 * This interface provides CRUD operations for Forecast objects and allows for custom query methods.
 * It extends JpaRepository, which provides a full set of JPA repository functionality.
 */
@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {

    /**
     * Finds a forecast for a specific date.
     * This method retrieves a forecast model that matches the provided date.
     * If no forecast exists for the given date, an empty Optional is returned.
     *
     * @param currentDate The date for which the forecast is to be retrieved,
     *                    formatted as yyyy-MM-dd.
     * @return An Optional containing the matching Forecast model if found,
     *         or an empty Optional if no forecast exists for the specified date.
     */
    Optional<Forecast> findByDate(String currentDate);

}

