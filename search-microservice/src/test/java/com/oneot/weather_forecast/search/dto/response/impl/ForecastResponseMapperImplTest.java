package com.oneot.weather_forecast.search.dto.response.impl;

import com.oneot.weather_forecast.search.dto.response.impl.ForecastResponseMapperImpl;
import com.oneot.weather_forecast.search.model.Day;
import com.oneot.weather_forecast.search.model.Night;
import com.oneot.weather_forecast.search.dto.response.ForecastResponse;
import com.oneot.weather_forecast.search.model.SearchForecast;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ForecastResponseMapperImplTest {

    private final ForecastResponseMapperImpl mapper = new ForecastResponseMapperImpl();

    /**
     * Test the conversion of a single QueryForecast model to a ForecastResponse DTO.
     */
    @Test
    public void testToForecastResponse() {
        // Create a mock QueryForecast model
        Day day = new Day();
        Night night = new Night();
        SearchForecast forecast = new SearchForecast(LocalDate.now(), day, night);

        // Convert the QueryForecast model to a ForecastResponse DTO
        ForecastResponse response = mapper.toForecastResponse(forecast);

        // Verify that the fields are set correctly
        assertEquals(forecast.getDate(), response.getDate());
        assertEquals(forecast.getDay().getPhenomenon(), response.getDay().getPhenomenon());
        assertEquals(forecast.getNight().getPhenomenon(), response.getNight().getPhenomenon());

    }

    /**
     * Test the conversion of a list of QueryForecast entities to a list of ForecastResponse DTOs.
     */
    @Test
    public void testToForecastResponseList() {
        // Create mock QueryForecast entities
        Day day = new Day();
        Night night = new Night();
        SearchForecast forecast1 = new SearchForecast(LocalDate.of(2024, 1, 1), day, night);
        SearchForecast forecast2 = new SearchForecast(LocalDate.of(2024, 1, 2), day, night);

        List<SearchForecast> forecasts = Arrays.asList(forecast1, forecast2);

        // Convert the list of QueryForecast entities to a list of ForecastResponse DTOs
        List<ForecastResponse> responses = mapper.toForecastResponseList(forecasts);

        // Verify that the size of the response list matches the input list
        assertEquals(forecasts.size(), responses.size());

        // Verify that each ForecastResponse corresponds to the correct QueryForecast model
        for (int i = 0; i < forecasts.size(); i++) {
            assertEquals(forecasts.get(i).getDate(), responses.get(i).getDate());
        }
    }
}