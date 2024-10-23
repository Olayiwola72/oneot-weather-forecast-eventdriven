package com.oneot.weather_forecast.write.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.oneot.weather_forecast.write.config.WeatherApiProperties;
import com.oneot.weather_forecast.write.dto.ForecastResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of the WeatherApiClient interface for fetching weather data
 * from an external XML-based weather API.
 * 
 * This class uses RestTemplate to make HTTP requests and XmlMapper to
 * deserialize the XML response into a ForecastResponse object.
 * 
 * As an implementation of WeatherApiClient, it provides the specific logic
 * for interacting with the XML-based weather API, adhering to the contract
 * defined by the interface.
 */
@Component
public class XmlWeatherApiClient implements WeatherApiClient {

    private final WeatherApiProperties weatherApiProperties;
    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper;

    /**
     * Constructs a new XmlWeatherApiClient with the specified properties and dependencies.
     *
     * @param weatherApiProperties Configuration properties for the weather API
     * @param restTemplate The RestTemplate used for making HTTP requests
     * @param xmlMapper The XmlMapper used for converting XML to Java objects
     */
    public XmlWeatherApiClient(
        WeatherApiProperties weatherApiProperties,
        RestTemplate restTemplate,
        XmlMapper xmlMapper
    ) {
        this.weatherApiProperties = weatherApiProperties;
        this.restTemplate = restTemplate;
        this.xmlMapper = xmlMapper;
    }

    /**
     * Fetches the weather forecast from the external XML-based weather API.
     *
     * This method constructs the API URL using the getApiUrl() method,
     * makes an HTTP GET request, and deserializes the XML response into
     * a ForecastResponse object.
     *
     * This method implements the fetchForecast() method defined in the
     * WeatherApiClient interface, ensuring that the contract is fulfilled.
     *
     * @return ForecastResponse containing the weather forecast data
     * @throws Exception if an error occurs while fetching or parsing the forecast
     */
    @Override
    public ForecastResponse fetchForecast() throws Exception {
        String xmlResponse = restTemplate.getForObject(getApiUrl(), String.class);
        return xmlMapper.readValue(xmlResponse, ForecastResponse.class);
    }

    /**
     * Retrieves the API URL used for making requests to the weather API.
     *
     * This method constructs the URL using the properties defined in
     * WeatherApiProperties.
     *
     * This method implements the getApiUrl() method defined in the
     * WeatherApiClient interface, ensuring that the contract is fulfilled.
     *
     * @return A string representing the API URL
     */
    @Override
    public String getApiUrl() {

        return weatherApiProperties.url() + "?lang=" + weatherApiProperties.lang();
    }
}
