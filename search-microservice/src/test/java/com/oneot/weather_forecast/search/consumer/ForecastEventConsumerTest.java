package com.oneot.weather_forecast.search.consumer;

import com.oneot.weather_forecast.common.enums.EventType;
import com.oneot.weather_forecast.common.event.ForecastEvent;
import com.oneot.weather_forecast.common.model.Forecast;
import com.oneot.weather_forecast.search.consumer.ForecastEventConsumer;
import com.oneot.weather_forecast.search.model.SearchForecast;
import com.oneot.weather_forecast.search.repository.ForecastRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ForecastEventConsumerTest {

    @Mock
    private ForecastRepository forecastRepository;

    @InjectMocks
    private ForecastEventConsumer forecastEventConsumer;

    private ForecastEvent forecastEvent;

    @BeforeEach
    void setUp() {
        // Initialize a sample Forecast object that can be used in tests
        forecastEvent = new ForecastEvent(EventType.FORECAST_CREATED, new Forecast());
    }

    @Test
    void testConsume() {
        // Act: Simulate the Kafka message consumption
        forecastEventConsumer.consume(forecastEvent);

        // Assert: Verify that the repository's save method was called with the converted QueryForecast object
        verify(forecastRepository, times(1)).save(any(SearchForecast.class));
    }
}
