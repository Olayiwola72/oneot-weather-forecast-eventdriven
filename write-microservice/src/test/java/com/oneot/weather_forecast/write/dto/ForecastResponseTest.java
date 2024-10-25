package com.oneot.weather_forecast.write.dto;

import com.oneot.weather_forecast.common.model.Day;
import com.oneot.weather_forecast.common.model.Forecast;
import com.oneot.weather_forecast.common.model.Night;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ForecastResponseTest {
    private ForecastResponse response;
    private Forecast forecast1;
    private Forecast forecast2;

    @BeforeEach
    void setUp(){
        // Create sample Forecast objects
        forecast1 = new Forecast("2024-10-21", new Day(), new Night());
        forecast2 = new Forecast("2024-10-22", new Day(), new Night());

        // Create a ForecastResponse object
        response = new ForecastResponse(Arrays.asList(forecast1, forecast2));
    }

    @Test
    void testGetters() {
        // Test the getter for forecasts
        assertEquals(2, response.forecasts().size(), "Expected 2 forecasts in the response");
        assertEquals(forecast1, response.forecasts().get(0), "First forecast should match forecast1");
        assertEquals(forecast2, response.forecasts().get(1), "Second forecast should match forecast2");
    }

}
