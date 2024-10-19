package com.oneot.weather_forecast.common.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Test class to test abstract WeatherAttributes, since it cannot be instantiated directly
class TestWeatherAttributes extends WeatherAttributes {

    TestWeatherAttributes() {
        super();
    }

    TestWeatherAttributes(String phenomenon, Integer tempMin) {
        super(phenomenon, tempMin);
    }
}

class WeatherAttributesTest {

    @Test
    void testNoArgsConstructor() {
        // Given: A WeatherAttributes object created using the no-args constructor
        TestWeatherAttributes attributes = new TestWeatherAttributes();

        // When: We haven't set any attributes yet
        // Then: The fields should be null or zero
        assertNull(attributes.getPhenomenon());
        assertNull(attributes.getTempMin());
    }

    @Test
    void testAllArgsConstructor() {
        // Given: A WeatherAttributes object created with all arguments constructor
        String expectedPhenomenon = "Clear";
        Integer expectedTempMin = 5;

        TestWeatherAttributes attributes = new TestWeatherAttributes(expectedPhenomenon, expectedTempMin);

        // Then: The fields should be set correctly
        assertEquals(expectedPhenomenon, attributes.getPhenomenon());
        assertEquals(expectedTempMin, attributes.getTempMin());
    }

    @Test
    void testSettersAndGetters() {
        // Given: A WeatherAttributes object
        String phenomenon = "Snow";
        Integer tempMin = -2;
        TestWeatherAttributes attributes = new TestWeatherAttributes();

        // When: We set the values using setters
        attributes.setPhenomenon(phenomenon);
        attributes.setTempMin(tempMin);

        // Then: The getters should return the correct values
        assertEquals(phenomenon, attributes.getPhenomenon());
        assertEquals(tempMin, attributes.getTempMin());
    }
}
