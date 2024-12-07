package com.oneot.weather_forecast.query.service;

import com.oneot.weather_forecast.query.model.Day;
import com.oneot.weather_forecast.query.model.QueryForecast;
import com.oneot.weather_forecast.query.model.Night;
import com.oneot.weather_forecast.query.model.Place;
import com.oneot.weather_forecast.query.repository.ForecastRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class ForecastServiceTest {

    @Mock
    private ForecastRepository forecastRepository; // Mocked repository

    @InjectMocks
    private ForecastService forecastService; // Service to be tested

    private String place;
    private String today;
    private QueryForecast queryForecast1;
    private QueryForecast queryForecast2;
    private List<QueryForecast> queryForecasts;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        // Setup test data
        String phenomenon = "Sunny";
        int tempMin = 15;
        int tempMax = 25;
        String text = "Clear sky";
        today = LocalDate.now().toString(); // Get today's date

        place = "Tallinn";

        List<Place> places = List.of(
                new Place(place, "Sunny", 20, 10)
        );
        String peipsi = "Southwest, west wind 4-8, in gusts up to 12 m/s.";

        queryForecast1 = new QueryForecast(
                today, // Create a forecast object for today
                new Day(phenomenon, tempMin, tempMax, text, places, peipsi),
                new Night(phenomenon, tempMin, tempMax, text, places, peipsi)
        );

        queryForecast2 = new QueryForecast(
                "2024-10-02",
                new Day(phenomenon, tempMin, tempMax, text, places, peipsi),
                new Night(phenomenon, tempMin, tempMax, text, places, peipsi)
        );

        queryForecasts = Arrays.asList(queryForecast1, queryForecast2);
    }

    @Test
    public void testGetAllForecastsByPlace() {
        // Given
        when(forecastRepository.findAllByPlace(place)).thenReturn(queryForecasts); // Mock repository method

        // When
        List<QueryForecast> result = forecastService.getAllForecastsByPlace(place); // Call the service method

        // Then
        assertThat(result).isNotNull(); // Check that the result is not null
        assertThat(result).hasSize(2); // Check that the result size is as expected
        assertThat(result).containsExactlyInAnyOrder(queryForecast1, queryForecast2); // Check the contents
    }

    @Test
    public void testGetTodayForecasts() {
        // Set other properties as needed
        when(forecastRepository.findByDate(today)).thenReturn(Optional.of(queryForecast1)); // Mock repository method

        // When
        Optional<QueryForecast> result = forecastService.getTodayForecast(); // Call the service method

        // Then
        assertThat(result).isNotNull(); // Check that the result is not null
        assertThat(result).isPresent(); // Check the contents
    }
    
}
