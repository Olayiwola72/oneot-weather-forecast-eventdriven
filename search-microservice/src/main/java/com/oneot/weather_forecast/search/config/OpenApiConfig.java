package com.oneot.weather_forecast.search.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * Configuration class for OpenAPI documentation.
 * This class sets up the OpenAPI definition for the application.
 */
@OpenAPIDefinition // Indicates that this class provides OpenAPI definitions
@Configuration // Marks this class as a source of bean definitions for the application context
public class OpenApiConfig {

    /**
     * Default constructor for OpenApiConfig.
     */
    public OpenApiConfig() {
        // Constructor can be used for any initialization if needed
    }
    
    /**
     * Creates a bean for the OpenAPI configuration.
     *
     * @param appVersion The version of the application, injected from application properties.
     * @param appTitle The title of the application, injected from application properties.
     * @param appDescription The description of the application, injected from application properties.
     * @return An OpenAPI instance configured with the application information.
     */
    @Bean
    public OpenAPI baseOpenAPI(
            @Value("${springdoc.version}") String appVersion,
            @Value("${springdoc.title}") String appTitle,
            @Value("${springdoc.description}") String appDescription
    ){
        return new OpenAPI()
                .info(new Info()
                        .title(appTitle) // Sets the title of the API
                        .version(appVersion) // Sets the version of the API
                        .description(appDescription) // Sets the description of the API
                );
    }
}
