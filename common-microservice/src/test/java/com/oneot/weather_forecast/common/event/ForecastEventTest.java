package com.oneot.weather_forecast.common.event;

// ... existing code ...
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.oneot.weather_forecast.common.enums.EventType;
import com.oneot.weather_forecast.common.model.Forecast;
import org.junit.jupiter.api.Test;

class ForecastEventTest {

    @Test
    void testForecastEventCreation() {
        // Arrange
        EventType eventType = EventType.FORECAST_CREATED;
        Forecast forecast = new Forecast(); // Assuming a default constructor exists

        // Act
        ForecastEvent forecastEvent = new ForecastEvent(eventType, forecast);

        // Assert
        assertEquals(eventType, forecastEvent.eventType());
        assertEquals(forecast, forecastEvent.forecast());
    }

}
