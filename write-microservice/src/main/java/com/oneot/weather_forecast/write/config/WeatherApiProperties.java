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
        String url,   // The base URL of the Weather API.
        String lang   // The language code for the weather data.
) {
}
