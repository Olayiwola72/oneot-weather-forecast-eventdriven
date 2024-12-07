package com.oneot.weather_forecast.write.service;

import com.oneot.weather_forecast.common.enums.EventType;
import com.oneot.weather_forecast.common.event.ForecastEvent;
import com.oneot.weather_forecast.common.model.Day;
import com.oneot.weather_forecast.common.model.Forecast;
import com.oneot.weather_forecast.common.model.Night;
import com.oneot.weather_forecast.write.producer.ForecastEventProducer;
import com.oneot.weather_forecast.write.repository.ForecastRepository;
import com.oneot.weather_forecast.write.dto.ForecastResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ForecastServiceTest {

    @Mock
    private ForecastRepository forecastRepository;

    @Mock
    private WeatherApiClient weatherApiClient;

    @Mock
    private ForecastEventProducer forecastEventProducer;

    @InjectMocks
    private ForecastService forecastService;

    private ForecastResponse mockForecastResponse;
    private Forecast forecast1;
    private Forecast forecast2;
    private ForecastEvent forecastEvent1;
    private ForecastEvent forecastEvent2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create sample Forecast objects
        forecast1 = new Forecast("2024-10-21", new Day(), new Night());
        forecast2 = new Forecast("2024-10-22", new Day(), new Night());

        // Create sample Forecast Event objects
        forecastEvent1 = new ForecastEvent(EventType.FORECAST_CREATED, forecast1);
        forecastEvent2 = new ForecastEvent(EventType.FORECAST_CREATED, forecast2);

        // Create a ForecastResponse object
        mockForecastResponse = new ForecastResponse(Arrays.asList(forecast1, forecast2));
    }

    @Test
    void testSaveWeatherForecastsSuccessfully() throws Exception {
        // Arrange: Mock the behavior of the weather API client
        when(weatherApiClient.fetchForecast()).thenReturn(mockForecastResponse);
        when(forecastRepository.save(forecast1)).thenReturn(forecast1); // Return saved forecast1
        when(forecastRepository.save(forecast2)).thenReturn(forecast2); // Return saved forecast2

        // Act
        forecastService.saveWeatherForecasts();

        // Verify the repository interaction
        verify(forecastRepository, times(1)).save(forecast1); // Verify that forecast1 was saved
        verify(forecastEventProducer, times(1)).sendForecastEvent(forecastEvent1); // Verify that the forecast was sent to Kafka

        verify(forecastRepository, times(1)).save(forecast2); // Verify that forecast1 was saved
        verify(forecastEventProducer, times(1)).sendForecastEvent(forecastEvent2); // Verify that the forecast was sent to Kafka

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
