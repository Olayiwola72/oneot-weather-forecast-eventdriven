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
import static org.mockito.Mockito.*;

class ForecastServiceTest {

    @Mock
    private ForecastRepository forecastRepository;

    @Mock
    private WeatherApiClient weatherApiClient;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private ForecastService forecastService;

    private ForecastResponse mockForecastResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create sample Forecast objects
        Forecast forecast1 = new Forecast("2024-10-21", new Day(), new Night());
        Forecast forecast2 = new Forecast("2024-10-22", new Day(), new Night());

        // Create a ForecastResponse object
        mockForecastResponse = new ForecastResponse(Arrays.asList(forecast1, forecast2));
    }

    @Test
    void testSaveWeatherForecastsSuccessfully() throws Exception {
        when(weatherApiClient.fetchForecast()).thenReturn(mockForecastResponse);

        // Act
        forecastService.saveWeatherForecasts();

        // Assert
        verify(forecastRepository).saveAll(mockForecastResponse.forecasts());
    }

    @Test
    void testSaveWeatherForecastsHandlesException() throws Exception {
        // Arrange
        when(weatherApiClient.fetchForecast()).thenThrow(new RuntimeException("API error"));

        // Act
        forecastService.saveWeatherForecasts();

        // Assert
        // Verify that saveAll was not called due to the exception
        verify(forecastRepository, never()).saveAll(any());
    }
}
