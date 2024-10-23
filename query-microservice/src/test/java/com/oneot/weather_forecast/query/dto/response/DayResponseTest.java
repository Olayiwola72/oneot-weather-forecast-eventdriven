package com.oneot.weather_forecast.query.dto.response;

import com.oneot.weather_forecast.common.entity.Day;
import com.oneot.weather_forecast.common.entity.Place;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DayResponseTest {

    /**
     * Test the creation of a DayResponse instance from a Day entity.
     */
    @Test
    public void testFromEntity() {
        // Create a mock Day entity
        String phenomenon = "Clear";
        int tempMin = 10;
        int tempMax = 15;
        List<Place> places = List.of(
                new Place("TestCity", phenomenon, tempMin, tempMax)
        );
        String text = "Clear night sky";
        String peipsi = "Calm waters";
        Day day = new Day(phenomenon, tempMin, tempMax, text, places, peipsi);

        // Convert the Day entity to a DayResponse DTO
        DayResponse dayResponse = DayResponse.fromEntity(day);

        // Verify that the fields are set correctly
        assertEquals(phenomenon, dayResponse.getPhenomenon());
        assertEquals(tempMin, dayResponse.getTempMin());
        assertEquals(tempMax, dayResponse.getTempMax());
        assertEquals(text, dayResponse.getText());
        assertEquals(peipsi, dayResponse.getPeipsi());
        assertNotNull(dayResponse.getPlaces()); // Ensure the places list is not null
        assertEquals(dayResponse.getPlaces().size(), places.size()); // Verify that the places list is empty
    }
}
