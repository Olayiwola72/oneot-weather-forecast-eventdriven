package com.oneot.weather_forecast.query.dto.response;

import com.oneot.weather_forecast.common.entity.Day;
import com.oneot.weather_forecast.common.entity.Forecast;
import com.oneot.weather_forecast.common.entity.Night;
import com.oneot.weather_forecast.common.entity.Place;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ForecastResponseTest {

    /**
     * Test the creation of a ForecastResponse instance from a Day entity.
     */
    @Test
    public void testFromEntity() {
        // Create a mock Forecast entity
        String date = "2023-05-15";
        List<Place> places = List.of(
                new Place("Tallinn", "Sunny", 20, 10)
        );
        String peipsi = "Calm waters, light breeze";
        Day day = new Day("Sunny", 20, 25, "Clear skies", places, peipsi);
        Night night = new Night("Clear", 15, 18, "Starry night", places, peipsi);
        Forecast forecast = new Forecast(date, day, night);

        // Convert the Forecast entity to a ForecastResponse DTO
        ForecastResponse forecastResponse = ForecastResponse.fromEntity(forecast);

        // Verify that the fields are set correctly
        assertEquals(date, forecastResponse.getDate());
        assertEquals(day.getPhenomenon(), forecastResponse.getDay().getPhenomenon());
        assertEquals(night.getPhenomenon(), forecastResponse.getNight().getPhenomenon());
    }
    
}
