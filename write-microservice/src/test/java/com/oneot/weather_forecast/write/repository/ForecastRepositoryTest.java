package com.oneot.weather_forecast.write.repository;

import com.oneot.weather_forecast.common.model.Day;
import com.oneot.weather_forecast.common.model.Forecast;
import com.oneot.weather_forecast.common.model.Night;
import com.oneot.weather_forecast.common.model.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class ForecastRepositoryTest {

    private String phenomenon;
    private Integer tempMin;
    private Integer tempMax;
    private String text;
    private String place;
    private List<Place> places;
    private String peipsi;

    @Autowired
    private ForecastRepository forecastRepository;

    @BeforeEach
    void setUp() {
        // Setup test data
        phenomenon = "Sunny";
        tempMin = 15;
        tempMax = 25;
        text = "Clear sky";
        place = "Tallinn";
        places = List.of(
                new Place(place, "Sunny", 20, 10)
        );
        peipsi = "Southwest, west wind 4-8, in gusts up to 12 m/s.";
    }

    @Test
    void testFindByDate() {
        //Arrange
        Forecast forecast = new Forecast(
                "2024-10-03",
                new Day(phenomenon, tempMin, tempMax, text, places, peipsi),
                new Night(phenomenon, tempMin, tempMax, text, places, peipsi)
        );
        forecastRepository.save(forecast);

        // Execute the method to test
        Optional<Forecast> result = forecastRepository.findByDate("2024-10-03");

        // Verify the results
        assertTrue(result.isPresent());
    }

}

