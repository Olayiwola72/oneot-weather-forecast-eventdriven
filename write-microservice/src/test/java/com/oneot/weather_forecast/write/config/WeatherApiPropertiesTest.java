package com.oneot.weather_forecast.write.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherApiPropertiesTest {

    @Test
    void testPropertiesAreCorrectlyBound() {
        String expectedUrl = "http://test.weather.api";
        String expectedLang = "en";
        WeatherApiProperties weatherApiProperties = new  WeatherApiProperties(expectedUrl, expectedLang);

        assertEquals(expectedUrl, weatherApiProperties.url());
        assertEquals(expectedLang, weatherApiProperties.lang());
    }
}
