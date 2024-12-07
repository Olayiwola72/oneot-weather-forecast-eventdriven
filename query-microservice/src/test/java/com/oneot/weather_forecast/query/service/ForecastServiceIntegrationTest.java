package com.oneot.weather_forecast.query.service;

import com.oneot.weather_forecast.query.model.Day;
import com.oneot.weather_forecast.query.model.QueryForecast;
import com.oneot.weather_forecast.query.model.Night;
import com.oneot.weather_forecast.query.model.Place;
import com.oneot.weather_forecast.query.repository.ForecastRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // Load the full application context
@ActiveProfiles({
        "dev",
        "test"
})
class ForecastServiceIntegrationTest {

    @Autowired
    private ForecastService forecastService; // Inject the service to be tested

    @Autowired
    private ForecastRepository forecastRepository; // Inject the repository to set up test data

    private String place;

    @BeforeEach
    void setUp() {
        // Setup test data
        String phenomenon = "Sunny";
        int tempMin = 15;
        int tempMax = 25;
        String text = "Clear sky";
        String today = LocalDate.now().toString(); // Get today's date

        place = "Tallinn";

        List<Place> places = List.of(
                new Place(place, "Sunny", 20, 10)
        );
        String peipsi = "Southwest, west wind 4-8, in gusts up to 12 m/s.";

        // Create a forecast object for today
        QueryForecast queryForecast1 = new QueryForecast(
                today, // Create a forecast object for today
                new Day(phenomenon, tempMin, tempMax, text, places, peipsi),
                new Night(phenomenon, tempMin, tempMax, text, places, peipsi)
        );
        forecastRepository.save(queryForecast1);

        places = List.of(
                new Place("Lagos", "Sunny", 20, 10)
        );

        QueryForecast queryForecast2 = new QueryForecast(
                "2024-10-02",
                new Day(phenomenon, tempMin, tempMax, text, places, peipsi),
                new Night(phenomenon, tempMin, tempMax, text, places, peipsi)
        );

        forecastRepository.save(queryForecast2);
    }

    @AfterEach
    void cleanUp(){
        // Clear the repository before each test
        forecastRepository.deleteAll();
    }

    @Test
     void testGetAllForecastsByPlace() {
        // When
        List<QueryForecast> result = forecastService.getAllForecastsByPlace(place);

        // Then
        assertThat(result).isNotNull(); // Check that the result is not null
        assertThat(result).hasSize(1); // Check that the result size is as expected
    }

    @Test
     void testGetTodayForecast() {
        // When
        Optional<QueryForecast> result = forecastService.getTodayForecast();

        // Then
        assertThat(result).isNotNull(); // Check that the result is not null
        assertThat(result).isPresent(); // Check that the result size is as expected
    }
}
