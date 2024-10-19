package com.oneot.weather_forecast.write.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "weather.api")
public record WeatherApiProperties(String url, String lang) {
	
}
