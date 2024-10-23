package com.oneot.weather_forecast.write.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.oneot.weather_forecast.common.entity.Day;
import com.oneot.weather_forecast.common.entity.Forecast;
import com.oneot.weather_forecast.common.entity.Night;
import com.oneot.weather_forecast.write.config.WeatherApiProperties;
import com.oneot.weather_forecast.write.dto.ForecastResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class XmlWeatherApiClientTest {

    @Mock
    private WeatherApiProperties weatherApiProperties;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private XmlMapper xmlMapper;

    @InjectMocks
    private XmlWeatherApiClient xmlWeatherApiClient;

    private ForecastResponse mockForecastResponse;
    private String apiBaseUrl;
    private String mockUrl;
    private String lang;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Arrange
        lang = "en";
        apiBaseUrl = "http://api.weather.com";
        mockUrl = apiBaseUrl+"?lang="+lang;

        // Create sample Forecast objects
        Forecast forecast1 = new Forecast("2024-10-21", new Day(), new Night());
        Forecast forecast2 = new Forecast("2024-10-22", new Day(), new Night());

        // Create a ForecastResponse object
        mockForecastResponse = new ForecastResponse(Arrays.asList(forecast1, forecast2));
    }

    @Test
    void testFetchForecast() throws Exception {
        // Arrange
        String mockXmlResponse = "<forecast>...</forecast>";

        when(weatherApiProperties.url()).thenReturn(apiBaseUrl);
        when(weatherApiProperties.lang()).thenReturn(lang);
        when(restTemplate.getForObject(mockUrl, String.class)).thenReturn(mockXmlResponse);
        when(xmlMapper.readValue(mockXmlResponse, ForecastResponse.class)).thenReturn(mockForecastResponse);

        // Act
        ForecastResponse result = xmlWeatherApiClient.fetchForecast();

        // Assert
        assertEquals(mockForecastResponse, result);
        verify(restTemplate).getForObject(mockUrl, String.class);
        verify(xmlMapper).readValue(mockXmlResponse, ForecastResponse.class);
    }

    @Test
    void testFetchForecastThrowsException() {
        // Arrange
        String errorMessage = "API error";
        when(weatherApiProperties.url()).thenReturn(apiBaseUrl);
        when(weatherApiProperties.lang()).thenReturn(lang);
        when(restTemplate.getForObject(mockUrl, String.class)).thenThrow(new RuntimeException(errorMessage));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> xmlWeatherApiClient.fetchForecast());

        // Optionally, you can check the message of the exception
        assertEquals(errorMessage, thrown.getMessage());
    }

    @Test
    void testGetApiUrl() {
        // Arrange
        when(weatherApiProperties.url()).thenReturn(apiBaseUrl);
        when(weatherApiProperties.lang()).thenReturn(lang);

        // Act
        String result = xmlWeatherApiClient.getApiUrl();

        // Assert
        assertEquals(mockUrl, result);
    }
}
