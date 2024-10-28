package com.oneot.weather_forecast.query.dto.response;

import com.oneot.weather_forecast.common.model.Place;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaceResponseTest {

    /**
     * Test the creation of a PlaceResponse instance from a Day model.
     */
    @Test
    public void testFromEntity() {
        // Create a mock Place model
        String name = "Tallinn";
        String phenomenon = "Clear";
        int tempMin = 10;
        int tempMax = 15;
        Place place = new Place(name, phenomenon, tempMin, tempMax);

        // Convert the Place model to a PlaceResponse DTO
        PlaceResponse placeResponse = PlaceResponse.fromEntity(place);

        // Verify that the fields are set correctly
        assertEquals(name, placeResponse.getName());
        assertEquals(phenomenon, placeResponse.getPhenomenon());
        assertEquals(tempMin, placeResponse.getTempMin());
        assertEquals(tempMax, placeResponse.getTempMax());
    }

}
