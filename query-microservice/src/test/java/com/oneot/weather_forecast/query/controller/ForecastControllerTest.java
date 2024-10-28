package com.oneot.weather_forecast.query.controller;

import com.oneot.weather_forecast.common.model.Day;
import com.oneot.weather_forecast.common.model.Forecast;
import com.oneot.weather_forecast.common.model.Night;
import com.oneot.weather_forecast.common.model.Place;
import com.oneot.weather_forecast.common.repository.ForecastRepository;
import com.oneot.weather_forecast.query.config.RouteConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // Load the full application context
@ActiveProfiles("test") // Use the 'test' profile for testing
@AutoConfigureMockMvc // Enable MockMvc
class ForecastControllerTest {

    @Autowired
    private ForecastRepository forecastRepository; // Inject the repository to set up test dat

    @Autowired
    private MockMvc mockMvc; // MockMvc to perform requests

    @Autowired
    private RouteConfig routeConfig;

    private String place;
    private String today;

    @BeforeEach
    void setUp() {
        // Clear the repository before each test
        forecastRepository.deleteAll();

        // Test data setup
        String phenomenon = "Sunny";
        int tempMin = 15;
        int tempMax = 25;
        String text = "Clear sky";
        today = LocalDate.now().toString(); // Get today's date
        place = "Tallinn";

        List<Place> places = List.of(
                new Place(place, "Sunny", 20, 10)
        );
        String peipsi = "Southwest, west wind 4-8, in gusts up to 12 m/s.";

        // Create a forecast object for today
        Forecast forecast1 = new Forecast(
                today, // Create a forecast object for today
                new Day(phenomenon, tempMin, tempMax, text, places, peipsi),
                new Night(phenomenon, tempMin, tempMax, text, places, peipsi)
        );

        forecastRepository.save(forecast1);

        places = List.of(
                new Place("Lagos", "Sunny", 20, 10)
        );

        Forecast forecast2 = new Forecast(
                "2022-10-24", // Create a forecast object for today
                new Day(phenomenon, tempMin, tempMax, text, places, peipsi),
                new Night(phenomenon, tempMin, tempMax, text, places, peipsi)
        );

        forecastRepository.save(forecast2);
    }

    @Test
    void testGetAllForecastsByPlace() throws Exception {
        // Perform a POST request to the /places endpoint using RouteConfig
        mockMvc.perform(get(routeConfig.base() + routeConfig.places() + "?place="+place) // Use POST for the request
                        .contentType(MediaType.APPLICATION_JSON)) // Set content type to JSON
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$", hasSize(1))); // Assert that the response size is 1
    }

    @Test
    void testGetTodayForecast() throws Exception {

        // Perform a GET request to the /today endpoint
        mockMvc.perform(get(routeConfig.base() + routeConfig.today())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$.date").value(today)); // Check the forecast's date. It must be today
    }

}
