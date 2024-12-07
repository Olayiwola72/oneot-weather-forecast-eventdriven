package com.oneot.weather_forecast.common.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTypeTest {

    @Test
    public void testEventTypeValues() {
        // Verify the number of enum constants
        assertEquals(2, EventType.values().length);

        // Verify the names of the enum constants
        assertEquals("FORECAST_CREATED", EventType.FORECAST_CREATED.name());
        assertEquals("FORECAST_UPDATED", EventType.FORECAST_UPDATED.name());
    }
}