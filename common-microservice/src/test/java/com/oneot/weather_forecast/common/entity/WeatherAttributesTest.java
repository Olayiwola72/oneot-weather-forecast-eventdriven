package com.oneot.weather_forecast.common.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Test class to test abstract WeatherAttributes, since it cannot be instantiated directly
class TestWeatherAttributes extends WeatherAttributes {

    TestWeatherAttributes() {
        super();
    }

    TestWeatherAttributes(String phenomenon, Integer tempMin, Integer tempMax) {
        super(phenomenon, tempMin, tempMax);
    }
}

class WeatherAttributesTest {

    private String expectedPhenomenon;
    private Integer expectedTempMin;
    private Integer expectedTempMax;

    @BeforeEach
    void setUp(){
        expectedPhenomenon = "Clear";
        expectedTempMin = -5;
        expectedTempMax = 10;
    }

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
        TestWeatherAttributes attributes = new TestWeatherAttributes(expectedPhenomenon, expectedTempMin, expectedTempMax);

        // Then: The fields should be set correctly
        assertEquals(expectedPhenomenon, attributes.getPhenomenon());
        assertEquals(expectedTempMin, attributes.getTempMin());
        assertEquals(expectedTempMax, attributes.getTempMax());
    }

    @Test
    void testSettersAndGetters() {
        // Given: A WeatherAttributes object
        TestWeatherAttributes attributes = new TestWeatherAttributes();

        // When: We set the values using setters
        attributes.setPhenomenon(expectedPhenomenon);
        attributes.setTempMin(expectedTempMin);
        attributes.setTempMax(expectedTempMax);

        // Then: The getters should return the correct values
        assertEquals(expectedPhenomenon, attributes.getPhenomenon());
        assertEquals(expectedTempMin, attributes.getTempMin());
        assertEquals(expectedTempMax, attributes.getTempMax());
    }
}
