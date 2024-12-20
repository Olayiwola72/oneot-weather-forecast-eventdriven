package com.oneot.weather_forecast.search.service;

import com.oneot.weather_forecast.search.model.Day;
import com.oneot.weather_forecast.search.model.SearchForecast;
import com.oneot.weather_forecast.search.model.Night;
import com.oneot.weather_forecast.search.model.Place;
import com.oneot.weather_forecast.search.repository.ForecastRepository;
import com.oneot.weather_forecast.search.service.ForecastService;
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
    private LocalDate today;
    private SearchForecast forecast1;
    private SearchForecast forecast2;
    private List<SearchForecast> forecasts;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        // Setup test data
        String phenomenon = "Sunny";
        int tempMin = 15;
        int tempMax = 25;
        String text = "Clear sky";
        today = LocalDate.now(); // Get today's date

        place = "Tallinn";

        List<Place> places = List.of(
                new Place(place, "Sunny", 20, 10)
        );
        String peipsi = "Southwest, west wind 4-8, in gusts up to 12 m/s.";

        forecast1 = new SearchForecast(
                today, // Create a forecast object for today
                new Day(phenomenon, tempMin, tempMax, text, places, peipsi),
                new Night(phenomenon, tempMin, tempMax, text, places, peipsi)
        );

        forecast2 = new SearchForecast(
                LocalDate.of(2024, 10, 2),
                new Day(phenomenon, tempMin, tempMax, text, places, peipsi),
                new Night(phenomenon, tempMin, tempMax, text, places, peipsi)
        );

        forecasts = Arrays.asList(forecast1, forecast2);
    }

    @Test
    public void testGetAllForecastsByPlace() {
        // Given
        when(forecastRepository.findAllByPlace(place)).thenReturn(forecasts); // Mock repository method

        // When
        List<SearchForecast> result = forecastService.getAllForecastsByPlace(place); // Call the service method

        // Then
        assertThat(result).isNotNull(); // Check that the result is not null
        assertThat(result).hasSize(2); // Check that the result size is as expected
        assertThat(result).containsExactlyInAnyOrder(forecast1, forecast2); // Check the contents
    }

    @Test
    public void testGetTodayForecasts() {
        // Set other properties as needed
        when(forecastRepository.findByDate(today)).thenReturn(Optional.of(forecast1)); // Mock repository method

        // When
        Optional<SearchForecast> result = forecastService.getTodayForecast(); // Call the service method

        // Then
        assertThat(result).isNotNull(); // Check that the result is not null
        assertThat(result).isPresent(); // Check the contents
    }
    
}
