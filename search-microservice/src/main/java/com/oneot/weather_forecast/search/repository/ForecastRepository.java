package com.oneot.weather_forecast.search.repository;

import com.oneot.weather_forecast.search.model.SearchForecast;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for SearchForecast entities.
 * This interface provides CRUD operations for SearchForecast objects and allows for custom query methods.
 * It extends ElasticsearchRepository, which provides a full set of Elasticsearch repository functionality.
 */
@Repository
public interface ForecastRepository extends ElasticsearchRepository<SearchForecast, String> {

    /**
     * Find all forecasts for a given location.
     * The location is matched against the places list in the WeatherPeriod (day or night).
     *
     * @param place The name of the Place to search for.
     * @return A list of forecasts matching the place.
     */
//    @Query("""
//    {
//      "bool": {
//        "should": [
//          {
//            "term": {
//              "day.places.name.keyword": "?0"
//            }
//          },
//          {
//            "term": {
//              "night.places.name.keyword": "?0"
//            }
//          }
//        ]
//      }
//    }
//    """)
    @Query("""
    {
      "bool": {
        "should": [
          {
            "match": {
              "day.places.name": "?0"
            }
          },
          {
            "match": {
              "night.places.name": "?0"
            }
          }
        ]
      }
    }
    """)
    List<SearchForecast> findAllByPlace(String place);

    /**
     * Finds a forecast for a specific date.
     * This method retrieves a forecast model that matches the provided date.
     * If no forecast exists for the given date, an empty Optional is returned.
     *
     * @param currentDate The date for which the forecast is to be retrieved,
     *                    formatted as yyyy-MM-dd.
     * @return An Optional containing the matching SearchForecast model if found,
     *         or an empty Optional if no forecast exists for the specified date.
     */
    Optional<SearchForecast> findByDate(LocalDate currentDate);

}
