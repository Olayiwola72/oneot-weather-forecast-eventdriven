package com.oneot.weather_forecast.query.dto.response;

import com.oneot.weather_forecast.common.entity.Day;
import com.oneot.weather_forecast.common.entity.Night;
import com.oneot.weather_forecast.common.entity.Place;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NightResponseTest {

    /**
     * Test the creation of a NightResponse instance from a Day entity.
     */
    @Test
    public void testFromEntity() {
        // Create a mock Night entity
        String phenomenon = "Clear";
        int tempMin = 10;
        int tempMax = 15;
        List<Place> places = List.of(
                new Place("TestCity", phenomenon, tempMin, tempMax)
        );
        String text = "Clear night sky";
        String peipsi = "Calm waters";
        Night night = new Night(phenomenon, tempMin, tempMax, text, places, peipsi);

        // Convert the Night entity to a NightResponse DTO
        NightResponse nightResponse = NightResponse.fromEntity(night);

        // Verify that the fields are set correctly
        assertEquals(phenomenon, nightResponse.getPhenomenon());
        assertEquals(tempMin, nightResponse.getTempMin());
        assertEquals(tempMax, nightResponse.getTempMax());
        assertEquals(text, nightResponse.getText());
        assertEquals(peipsi, nightResponse.getPeipsi());
        assertNotNull(nightResponse.getPlaces()); // Ensure the places list is not null
        assertEquals(nightResponse.getPlaces().size(), places.size()); // Verify that the places list is empty
    }
}
