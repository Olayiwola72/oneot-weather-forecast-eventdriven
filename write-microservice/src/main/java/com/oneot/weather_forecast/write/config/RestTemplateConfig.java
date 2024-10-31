package com.oneot.weather_forecast.write.config;

import com.oneot.weather_forecast.write.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
        // Set up RestTemplate with buffering to allow re-reading the response body
        RestTemplate restTemplate = new RestTemplate(
                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

        restTemplate.setInterceptors(List.of(new RequestInterceptor())); // Add custom the interceptor for logging
        return restTemplate;
    }
    
}
