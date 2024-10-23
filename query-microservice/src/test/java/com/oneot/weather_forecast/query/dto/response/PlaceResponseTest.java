package com.oneot.weather_forecast.query.dto.response;

import com.oneot.weather_forecast.common.entity.Night;
import com.oneot.weather_forecast.common.entity.Place;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlaceResponseTest {

    /**
     * Test the creation of a PlaceResponse instance from a Day entity.
     */
    @Test
    public void testFromEntity() {
        // Create a mock Place entity
        String name = "Tallinn";
        String phenomenon = "Clear";
        int tempMin = 10;
        int tempMax = 15;
        Place place = new Place(name, phenomenon, tempMin, tempMax);

        // Convert the Place entity to a PlaceResponse DTO
        PlaceResponse placeResponse = PlaceResponse.fromEntity(place);

        // Verify that the fields are set correctly
        assertEquals(name, placeResponse.getName());
        assertEquals(phenomenon, placeResponse.getPhenomenon());
        assertEquals(tempMin, placeResponse.getTempMin());
        assertEquals(tempMax, placeResponse.getTempMax());
    }

}
