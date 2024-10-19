package com.oneot.weather_forecast.common.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class DayTest {

    private String phenomenon;
    private int tempMin;
    private int tempMax;
    private List<Place> places;
    private String text;
    private Peipsi peipsi;
    private Day day;

    @BeforeEach
    void setUp() {
        phenomenon = "Clear";
        tempMin = 10;
        tempMax = 15;
        places = List.of(
                new Place("TestCity", phenomenon, tempMin)
        );
        text = "Clear night sky";
        peipsi = new Peipsi("Calm waters");
        day = new Day(phenomenon, tempMin, tempMax, text, places, peipsi);
    }

    @Test
    void testConstructor() {
        assertEquals(phenomenon, day.getPhenomenon());
        assertEquals(tempMin, day.getTempMin());
        assertEquals(tempMax, day.getTempMax());
        assertEquals(places, day.getPlaces());
        assertEquals(text, day.getText());
        assertEquals(peipsi, day.getPeipsi());
    }

    @Test
    void testInheritance() {
        assertInstanceOf(WeatherPeriod.class, day);
    }
}
