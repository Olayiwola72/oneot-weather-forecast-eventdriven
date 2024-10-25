package com.oneot.weather_forecast.common.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NightTest {

    private String phenomenon;
    private int tempMin;
    private int tempMax;
    private List<Place> places;
    private String text;
    private String peipsi;
    private Night night;

    @BeforeEach
    void setUp() {
        phenomenon = "Clear";
        tempMin = 10;
        tempMax = 15;
        places = List.of(
                new Place("TestCity", phenomenon, tempMin, tempMax)
        );
        text = "Clear night sky";
        peipsi = "Calm waters";
        night = new Night(phenomenon, tempMin, tempMax, text, places, peipsi);

    }

    @Test
    void testConstructor() {
        assertEquals(phenomenon, night.getPhenomenon());
        assertEquals(tempMin, night.getTempMin());
        assertEquals(tempMax, night.getTempMax());
        assertEquals(places, night.getPlaces());
        assertEquals(text, night.getText());
        assertEquals(peipsi, night.getPeipsi());
    }

    @Test
    void testInheritance() {
        assertInstanceOf(WeatherPeriod.class, night);
    }
}
