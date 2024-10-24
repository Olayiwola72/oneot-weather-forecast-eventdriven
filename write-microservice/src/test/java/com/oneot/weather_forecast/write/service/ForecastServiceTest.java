package com.oneot.weather_forecast.write.service;

import com.oneot.weather_forecast.common.entity.Day;
import com.oneot.weather_forecast.common.entity.Forecast;
import com.oneot.weather_forecast.common.entity.Night;
import com.oneot.weather_forecast.common.repository.ForecastRepository;
import com.oneot.weather_forecast.write.dto.ForecastResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ForecastServiceTest {

    @Mock
    private ForecastRepository forecastRepository;

    @Mock
    private WeatherApiClient weatherApiClient;

    @InjectMocks
    private ForecastService forecastService;

    private ForecastResponse mockForecastResponse;
    private Forecast forecast1;
    private Forecast forecast2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create sample Forecast objects
        forecast1 = new Forecast("2024-10-21", new Day(), new Night());
        forecast2 = new Forecast("2024-10-22", new Day(), new Night());

        // Create a ForecastResponse object
        mockForecastResponse = new ForecastResponse(Arrays.asList(forecast1, forecast2));
    }

    @Test
    void testSaveWeatherForecastsSuccessfully() throws Exception {
        when(weatherApiClient.fetchForecast()).thenReturn(mockForecastResponse);

        // Act
        forecastService.saveWeatherForecasts();

        // Verify the repository interaction
        verify(forecastRepository, times(1)).save(forecast1);
        verify(forecastRepository, times(1)).save(forecast2);
    }

    @Test
    void testSaveWeatherForecastsHandlesException() throws Exception {
        // Arrange
        when(weatherApiClient.fetchForecast()).thenThrow(new RuntimeException("API error"));

        // Call the method under test and expect no repository interactions
        assertThrows(Exception.class, () -> forecastService.saveWeatherForecasts());

        // Verify no interaction with the repository
        verify(forecastRepository, times(0)).save(any(Forecast.class));
    }

    @Test
    void saveWeatherForecasts_emptyResponse_doesNothing() throws Exception {
        // Mock an empty API response
        when(weatherApiClient.fetchForecast()).thenReturn(new ForecastResponse(List.of()));

        // Call the method under test
        forecastService.saveWeatherForecasts();

        // Verify no interaction with the repository
        verify(forecastRepository, times(0)).save(any(Forecast.class));
    }
}
