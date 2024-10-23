package com.oneot.weather_forecast.write.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for setting up the RestTemplate.
 * This class provides a custom configuration for creating a RestTemplate bean,
 * which is used for making HTTP requests to external services.
 */
@Configuration
public class RestTemplateConfig {
	
    /**
     * Creates and configures a RestTemplate bean.
     * RestTemplate is a synchronous client to perform HTTP requests, exposing a simple,
     * template method API over underlying HTTP client libraries.
     *
     * @return A configured RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
}
