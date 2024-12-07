package com.oneot.weather_forecast.query.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ForecastTest {

    private String date;
    private Day day;
    private Night night;

    @BeforeEach
    void setUp() {
        date = "2023-05-15";
        List<Place> places = List.of(
                new Place("Tallinn", "Sunny", 20, 10)
        );
        String peipsi = "Calm waters, light breeze";
        day = new Day("Sunny", 20, 25, "Clear skies", places, peipsi);
        night = new Night("Clear", 15, 18, "Starry night", places, peipsi);
    }

    @Test
    void testNoArgsConstructor() {
        QueryForecast emptyForecast = new QueryForecast();

        assertNull(emptyForecast.getId());
        assertNull(emptyForecast.getDate());
        assertNull(emptyForecast.getDay());
        assertNull(emptyForecast.getNight());
    }

    @Test
    void testArgsConstructor() {
        QueryForecast forecast = new QueryForecast(date, day, night);

        assertEquals(date, forecast.getDate());
        assertEquals(day, forecast.getDay());
        assertEquals(night, forecast.getNight());
    }

    @Test
    void testSetters() {
        QueryForecast forecast = new QueryForecast();

        forecast.setDate(date);
        forecast.setDay(day);
        forecast.setNight(night);

        assertEquals(date, forecast.getDate());
        assertEquals(day, forecast.getDay());
        assertEquals(night , forecast.getNight());
    }
}
