package com.oneot.weather_forecast.query.repository;

import com.oneot.weather_forecast.query.model.Day;
import com.oneot.weather_forecast.query.model.Night;
import com.oneot.weather_forecast.query.model.Place;
import com.oneot.weather_forecast.query.model.QueryForecast;
import com.oneot.weather_forecast.query.testcontainers.MongoTestContainer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class ForecastRepositoryTest extends  MongoTestContainer {

    private String phenomenon;
    private Integer tempMin;
    private Integer tempMax;
    private String text;
    private String place;
    private List<Place> places;
    private String peipsi;

    @Autowired
    private ForecastRepository forecastRepository;

    @BeforeEach
    void setUp() {
        // Setup test data
        phenomenon = "Sunny";
        tempMin = 15;
        tempMax = 25;
        text = "Clear sky";
        place = "Tallinn";
        places = List.of(
                new Place(place, "Sunny", 20, 10)
        );
        peipsi = "Southwest, west wind 4-8, in gusts up to 12 m/s.";
    }

    @AfterEach
    void tearDown() {
        forecastRepository.deleteAll();
    }

    @Test
    void testFindAllByPlace() {
        //Arrange
        QueryForecast forecast = new QueryForecast(
                "2024-10-01",
                new Day(phenomenon, tempMin, tempMax, text, places, peipsi),
                new Night(phenomenon, tempMin, tempMax, text, places, peipsi)
        );
        forecastRepository.save(forecast);

        // Execute the method to test
        List<QueryForecast> results = forecastRepository.findAllByPlace(place);

        // Verify the results
        assertThat(results).hasSize(1); // Expecting 1 forecasts for this place
    }

    @Test
    void testFindByDate() {
        //Arrange
        QueryForecast forecast = new QueryForecast(
                "2024-10-03",
                new Day(phenomenon, tempMin, tempMax, text, places, peipsi),
                new Night(phenomenon, tempMin, tempMax, text, places, peipsi)
        );
        forecastRepository.save(forecast);

        // Execute the method to test
        Optional<QueryForecast> result = forecastRepository.findByDate("2024-10-03");

        // Verify the results
        assertTrue(result.isPresent());
    }

}

