package com.oneot.weather_forecast.common.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeatherPeriodTest {

    private String phenomenon;
    private Integer tempMin;
    private Integer tempMax;
    private String text;
    private List<Place> places;
    private String peipsi;

    @BeforeEach
    void setUp(){
        phenomenon = "Sunny";
        tempMin = 15;
        tempMax = 25;
        text = "Clear sky";
        places = Arrays.asList(
                new Place("Tallinn", "Sunny", 20, 10)
        );
        peipsi = "Southwest, west wind 4-8, in gusts up to 12 m/s.";
    }

    @Test
    void testNoArgumentConstructor() {
        WeatherPeriod period = new WeatherPeriod();

        assertNull(period.getPhenomenon());
        assertNull(period.getTempMin());
        assertNull(period.getTempMax());
        assertNull(period.getPlaces());
        assertNull(period.getText());
        assertNull(period.getPeipsi());
    }

    @Test
    void testArgumentConstructor() {
        WeatherPeriod period = new WeatherPeriod(phenomenon, tempMin, tempMax, text, places, peipsi);

        assertEquals(phenomenon, period.getPhenomenon());
        assertEquals(tempMin, period.getTempMin());
        assertEquals(tempMax, period.getTempMax());
        assertEquals(places, period.getPlaces());
        assertEquals(text, period.getText());
        assertEquals(peipsi, period.getPeipsi());
    }

    @Test
    void testSetters() {
        WeatherPeriod period = new WeatherPeriod();

        period.setPhenomenon(phenomenon);
        period.setTempMin(tempMin);
        period.setTempMax(tempMax);
        period.setPlaces(places);
        period.setText(text);
        period.setPeipsi(peipsi);

        assertEquals(phenomenon, period.getPhenomenon());
        assertEquals(tempMin, period.getTempMin());
        assertEquals(tempMax, period.getTempMax());
        assertEquals(places, period.getPlaces());
        assertEquals(text, period.getText());
        assertEquals(peipsi, period.getPeipsi());
    }

    @Test
    void testInheritance() {
        WeatherPeriod period = new WeatherPeriod();
        assertInstanceOf(WeatherAttributes.class, period);
    }
}
