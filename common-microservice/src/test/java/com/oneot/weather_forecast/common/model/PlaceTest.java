package com.oneot.weather_forecast.common.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlaceTest {
    private Long id;
    private String name;
    private String phenomenon;
    private Integer tempMin;
    private Integer tempMax;

    @BeforeEach
    void setUp() {
        id = 1L;
        name = "Tallinn";
        phenomenon = "Cloudy";
        tempMin = 15;
        tempMax = 20;
    }

    @Test
    void testNoArgsConstructor() {
        Place place = new Place();

        assertNull(place.getId());
        assertNull(place.getName());
        assertNull(place.getPhenomenon());
        assertNull(place.getTempMin());
    }

    @Test
    void testAllArgsConstructor() {
        Place place = new Place(name, phenomenon, tempMin, tempMax);

        assertEquals(name, place.getName());
        assertEquals(phenomenon, place.getPhenomenon());
        assertEquals(tempMin, place.getTempMin());
    }

    @Test
    void testSettersAndGetters() {
        Place place = new Place();

        place.setId(id);
        place.setName(name);
        place.setPhenomenon(phenomenon);
        place.setTempMin(tempMin);

        assertEquals(id, place.getId());
        assertEquals(name, place.getName());
        assertEquals(phenomenon, place.getPhenomenon());
        assertEquals(tempMin, place.getTempMin());
    }

    @Test
    void testInheritance() {
        Place place = new Place();
        assertInstanceOf(WeatherAttributes.class, place);
    }
}