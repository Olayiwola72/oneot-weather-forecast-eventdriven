package com.oneot.weather_forecast.query.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for API routes.
 * This class holds the configuration properties related to API routes
 * that can be defined in the application properties or YAML file.
 */
@ConfigurationProperties(prefix = "routes.api") // Binds properties with the prefix 'routes.api' to this class
public record RouteConfig(
        String v1BaseUrl,   // Base URL for the API
        String today,  // Endpoint for today's weather forecast
        String places  // Endpoint for places-related information
) {
    // No additional methods or logic are needed for this record class.
}
