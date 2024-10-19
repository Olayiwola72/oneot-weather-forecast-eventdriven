package com.oneot.weather_forecast.common.repository;

import com.oneot.weather_forecast.common.entity.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Forecast entities.
 * This interface provides CRUD operations for Forecast objects and allows for custom query methods.
 * It extends JpaRepository, which provides a full set of JPA repository functionality.
 */
public interface ForecastRepository extends JpaRepository<Forecast, Long> {

}
