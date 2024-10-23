package com.oneot.weather_forecast.write.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the Weather API.
 * This record class is used to externalize configuration for the Weather API,
 * allowing these properties to be set in the application's configuration files
 * (e.g., application.properties or application.yml).
 *
 * The @ConfigurationProperties annotation binds the properties with the prefix "weather.api"
 * to the fields of this class.
 */
@ConfigurationProperties(prefix = "weather.api")
public record WeatherApiProperties(
    /**
     * The base URL of the Weather API.
     * This should be the endpoint where weather data can be fetched.
     */
    String url,

    /**
     * The language code for the weather data.
     * This determines the language of the weather information returned by the API.
     */
    String lang
) {
    // This is a record class, so the constructor, getters, equals, hashCode, and toString
    // methods are automatically generated.
}
