package com.oneot.weather_forecast.write.service;

import com.oneot.weather_forecast.common.model.Day;
import com.oneot.weather_forecast.common.model.Forecast;
import com.oneot.weather_forecast.common.model.Night;
import com.oneot.weather_forecast.write.dto.ForecastResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WeatherApiClientTest {

    private ForecastResponse sampleResponse;
    private String expectedApiUrl;

    @BeforeEach
    void setUp(){
        // Setup api base url
        expectedApiUrl = "http://example.com/api";
        // Create sample Forecast objects
        Forecast forecast1 = new Forecast("2024-10-21", new Day(), new Night());
        Forecast forecast2 = new Forecast("2024-10-22", new Day(), new Night());

        // Create a ForecastResponse object
        sampleResponse = new ForecastResponse(Arrays.asList(forecast1, forecast2));
    }

    /**
     * Test the WeatherApiClient interface implementation.
     * This test verifies that the fetchForecast and getApiUrl methods
     * return the expected results when mocked.
     */
    @Test
    void testWeatherApiClient() throws Exception {
        // Create a mock implementation of WeatherApiClient
        WeatherApiClient weatherApiClient = mock(WeatherApiClient.class);

        // Define the behavior of the mock for fetchForecast
        when(weatherApiClient.fetchForecast()).thenReturn(sampleResponse);

        // Define the behavior of the mock for getApiUrl
        when(weatherApiClient.getApiUrl()).thenReturn(expectedApiUrl);

        // Test the fetchForecast method
        ForecastResponse response = weatherApiClient.fetchForecast();
        assertEquals(sampleResponse, response, "The fetched forecast should match the sample response");

        // Test the getApiUrl method
        assertEquals(weatherApiClient.getApiUrl(), expectedApiUrl, "The API URL should match the expected URL");
    }
}
