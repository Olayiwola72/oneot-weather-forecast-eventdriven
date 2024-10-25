package com.oneot.weather_forecast.query.dto.response.impl;

import com.oneot.weather_forecast.common.model.Day;
import com.oneot.weather_forecast.common.model.Forecast;
import com.oneot.weather_forecast.common.model.Night;
import com.oneot.weather_forecast.query.dto.response.ForecastResponse;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ForecastResponseMapperImplTest {

    private final ForecastResponseMapperImpl mapper = new ForecastResponseMapperImpl();

    /**
     * Test the conversion of a single Forecast model to a ForecastResponse DTO.
     */
    @Test
    public void testToForecastResponse() {
        // Create a mock Forecast model
        Day day = new Day();
        Night night = new Night();
        Forecast forecast = new Forecast("2024-01-01", day, night);

        // Convert the Forecast model to a ForecastResponse DTO
        ForecastResponse response = mapper.toForecastResponse(forecast);

        // Verify that the fields are set correctly
        assertEquals(forecast.getDate(), response.getDate());
        assertEquals(forecast.getDay().getPhenomenon(), response.getDay().getPhenomenon());
        assertEquals(forecast.getNight().getPhenomenon(), response.getNight().getPhenomenon());

    }

    /**
     * Test the conversion of a list of Forecast entities to a list of ForecastResponse DTOs.
     */
    @Test
    public void testToForecastResponseList() {
        // Create mock Forecast entities
        Day day = new Day();
        Night night = new Night();
        Forecast forecast1 = new Forecast("2024-01-01", day, night);
        Forecast forecast2 = new Forecast("2024-01-02", day, night);

        List<Forecast> forecasts = Arrays.asList(forecast1, forecast2);

        // Convert the list of Forecast entities to a list of ForecastResponse DTOs
        List<ForecastResponse> responses = mapper.toForecastResponseList(forecasts);

        // Verify that the size of the response list matches the input list
        assertEquals(forecasts.size(), responses.size());

        // Verify that each ForecastResponse corresponds to the correct Forecast model
        for (int i = 0; i < forecasts.size(); i++) {
            assertEquals(forecasts.get(i).getDate(), responses.get(i).getDate());
        }
    }
}