package com.oneot.weather_forecast.query.controller;

import com.oneot.weather_forecast.query.model.Day;
import com.oneot.weather_forecast.query.model.QueryForecast;
import com.oneot.weather_forecast.query.model.Night;
import com.oneot.weather_forecast.query.model.Place;
import com.oneot.weather_forecast.query.repository.ForecastRepository;
import com.oneot.weather_forecast.query.config.RouteConfig;
import org.junit.jupiter.api.AfterEach;
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
@ActiveProfiles("test") // using 'test' profile
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
        QueryForecast queryForecast1 = new QueryForecast(
                today, // Create a forecast object for today
                new Day(phenomenon, tempMin, tempMax, text, places, peipsi),
                new Night(phenomenon, tempMin, tempMax, text, places, peipsi)
        );

        forecastRepository.save(queryForecast1);

        places = List.of(
                new Place("Lagos", "Sunny", 20, 10)
        );

        QueryForecast queryForecast2 = new QueryForecast(
                "2022-10-24", // Create a forecast object for today
                new Day(phenomenon, tempMin, tempMax, text, places, peipsi),
                new Night(phenomenon, tempMin, tempMax, text, places, peipsi)
        );

        forecastRepository.save(queryForecast2);
    }

    @AfterEach
    void tearDown() {
        forecastRepository.deleteAll();
    }

    @Test
    void testGetAllForecastsByPlace() throws Exception {
        // Perform a GET request to the /places endpoint
        mockMvc.perform(get(routeConfig.v1BaseUrl() + routeConfig.places() + "?place=" + place)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$.status").value(200)) // Assert status in the response wrapper
                .andExpect(jsonPath("$.message").isNotEmpty()) // Assert message in not empty in the response wrapper
                .andExpect(jsonPath("$.data", hasSize(1))); // Assert that the data array size is 1
    }

    @Test
    void testGetTodayForecast() throws Exception {
        // Perform a GET request to the /today endpoint
        mockMvc.perform(get(routeConfig.v1BaseUrl() + routeConfig.today())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(jsonPath("$.status").value(200)) // Assert status in the response wrapper
                .andExpect(jsonPath("$.message").isNotEmpty()) // Assert message in not empty in the response wrapper
                .andExpect(jsonPath("$.data.date").value(today)); // Check the forecast's date in the data section. It must be today
    }

}
