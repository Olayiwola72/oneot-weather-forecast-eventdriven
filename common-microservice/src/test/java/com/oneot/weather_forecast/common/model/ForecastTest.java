package com.oneot.weather_forecast.common.model;

import com.oneot.weather_forecast.common.model.Day;
import com.oneot.weather_forecast.common.model.Night;
import com.oneot.weather_forecast.common.model.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
        Forecast emptyForecast = new Forecast();

        assertNull(emptyForecast.getId());
        assertNull(emptyForecast.getDate());
        assertNull(emptyForecast.getDay());
        assertNull(emptyForecast.getNight());
    }

    @Test
    void testArgsConstructor() {
        Forecast forecast = new Forecast(date, day, night);

        assertEquals(date, forecast.getDate());
        assertEquals(day, forecast.getDay());
        assertEquals(night, forecast.getNight());
    }

    @Test
    void testSetters() {
        Forecast forecast = new Forecast();

        forecast.setDate(date);
        forecast.setDay(day);
        forecast.setNight(night);

        assertEquals(date, forecast.getDate());
        assertEquals(day, forecast.getDay());
        assertEquals(night , forecast.getNight());
    }
}
