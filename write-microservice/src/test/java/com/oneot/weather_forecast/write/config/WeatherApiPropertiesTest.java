package com.oneot.weather_forecast.write.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = {
    "weather.api.url=http://test.weather.api",
    "weather.api.lang=en"
})
class WeatherApiPropertiesTest {

    @Autowired
    private WeatherApiProperties weatherApiProperties;

    @Test
    void testPropertiesAreCorrectlyBound() {
        String expectedUrl = "http://test.weather.api";
        String expectedLang = "en";

        assertEquals(expectedUrl, weatherApiProperties.url());
        assertEquals(expectedLang, weatherApiProperties.lang());
    }
}
