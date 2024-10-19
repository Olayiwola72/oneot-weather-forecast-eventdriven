package com.oneot.weather_forecast.common.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PeipsiTest {

    private String forecastText;

    @BeforeEach
    void setUp() {
        forecastText = "Southerly wind 4-8 m/s. Wave height 0.4-0.9 m";
    }

    @Test
    void testNoArgsConstructor() {
        Peipsi peipsi = new Peipsi();
        assertNull(peipsi.getForecastText());
    }

    @Test
    void testAllArgsConstructor() {
        Peipsi peipsi = new Peipsi(forecastText);
        assertEquals(forecastText, peipsi.getForecastText());
    }

    @Test
    void testSetterAndGetter() {
        Peipsi peipsi = new Peipsi();

        peipsi.setForecastText(forecastText);
        assertEquals(forecastText, peipsi.getForecastText());
    }

}
