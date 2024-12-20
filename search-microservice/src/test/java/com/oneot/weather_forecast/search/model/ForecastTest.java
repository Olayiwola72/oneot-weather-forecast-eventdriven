package com.oneot.weather_forecast.search.model;

import com.oneot.weather_forecast.search.model.Day;
import com.oneot.weather_forecast.search.model.Night;
import com.oneot.weather_forecast.search.model.Place;
import com.oneot.weather_forecast.search.model.SearchForecast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ForecastTest {

    private LocalDate date;
    private Day day;
    private Night night;

    @BeforeEach
    void setUp() {
        date = LocalDate.of(2023, 5, 15);
        List<Place> places = List.of(
                new Place("Tallinn", "Sunny", 20, 10)
        );
        String peipsi = "Calm waters, light breeze";
        day = new Day("Sunny", 20, 25, "Clear skies", places, peipsi);
        night = new Night("Clear", 15, 18, "Starry night", places, peipsi);
    }

    @Test
    void testNoArgsConstructor() {
        SearchForecast emptyForecast = new SearchForecast();

        assertNull(emptyForecast.getId());
        assertNull(emptyForecast.getDate());
        assertNull(emptyForecast.getDay());
        assertNull(emptyForecast.getNight());
    }

    @Test
    void testArgsConstructor() {
        SearchForecast forecast = new SearchForecast(date, day, night);

        assertEquals(date, forecast.getDate());
        assertEquals(day, forecast.getDay());
        assertEquals(night, forecast.getNight());
    }

    @Test
    void testSetters() {
        SearchForecast forecast = new SearchForecast();

        forecast.setDate(date);
        forecast.setDay(day);
        forecast.setNight(night);

        assertEquals(date, forecast.getDate());
        assertEquals(day, forecast.getDay());
        assertEquals(night , forecast.getNight());
    }
}
