package com.oneot.weather_forecast.query.repository;

import com.oneot.weather_forecast.query.model.QueryForecast;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for QueryForecast entities.
 * This interface provides CRUD operations for QueryForecast objects and allows for custom query methods.
 * It extends MongoRepository, which provides a full set of MongoDB repository functionality.
 */
@Repository
public interface ForecastRepository extends MongoRepository<QueryForecast, String> {

  /**
   * Find all forecasts for a given location.
   * The location is matched against the places list in the WeatherPeriod (day or night).
   *
   * @param place The name of the Place to search for.
   * @return A list of forecasts matching the place.
   */
  @Query("{ '$or': [ { 'day.places.name': ?0 }, { 'night.places.name': ?0 } ] }")
  List<QueryForecast> findAllByPlace(String place);

  /**
   * Finds a forecast for a specific date.
   * This method retrieves a forecast model that matches the provided date.
   * If no forecast exists for the given date, an empty Optional is returned.
   *
   * @param currentDate The date for which the forecast is to be retrieved,
   *                    formatted as yyyy-MM-dd.
   * @return An Optional containing the matching QueryForecast model if found,
   *         or an empty Optional if no forecast exists for the specified date.
   */
  Optional<QueryForecast> findByDate(String currentDate);

}
