package com.oneot.weather_forecast.search.repository;

import com.oneot.weather_forecast.search.SearchServiceApplication;
import com.oneot.weather_forecast.search.model.Day;
import com.oneot.weather_forecast.search.model.Night;
import com.oneot.weather_forecast.search.model.Place;
import com.oneot.weather_forecast.search.model.SearchForecast;
import com.oneot.weather_forecast.search.testcontainers.ElasticsearchTestContainer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.elasticsearch.DataElasticsearchTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataElasticsearchTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SearchServiceApplication.class})
@ActiveProfiles({"dev", "test"})
class ForecastRepositoryTest {

    private String phenomenon;
    private Integer tempMin;
    private Integer tempMax;
    private String text;
    private String place;
    private List<Place> places;
    private String peipsi;

    static {
        ElasticsearchTestContainer.getContainer(); // Ensure container is started
    }

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
        SearchForecast forecast = new SearchForecast(
                LocalDate.of(2024, 10, 1),
                new Day(phenomenon, tempMin, tempMax, text, places, peipsi),
                new Night(phenomenon, tempMin, tempMax, text, places, peipsi)
        );
        forecastRepository.save(forecast);

        // Execute the method to test
        List<SearchForecast> results = forecastRepository.findAllByPlace(place);

        // Verify the results
        assertThat(results).hasSize(1); // Expecting 1 forecasts for this place
    }

    @Test
    void testFindByDate() {
        //Arrange
        LocalDate date = LocalDate.of(2024, 10, 3);
        SearchForecast forecast = new SearchForecast(
                date,
                new Day(phenomenon, tempMin, tempMax, text, places, peipsi),
                new Night(phenomenon, tempMin, tempMax, text, places, peipsi)
        );
        forecastRepository.save(forecast);

        // Execute the method to test
        Optional<SearchForecast> result = forecastRepository.findByDate(date);

        // Verify the results
        assertTrue(result.isPresent());
    }

}

