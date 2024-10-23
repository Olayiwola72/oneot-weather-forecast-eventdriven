package com.oneot.weather_forecast.write.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Jackson-related beans.
 * This class provides custom configurations for JSON and XML processing using Jackson.
 */
@Configuration
public class JacksonConfig {

    /**
     * Creates and configures an XmlMapper bean.
     * XmlMapper is used for XML serialization and deserialization.
     *
     * @return A configured XmlMapper instance
     */
    @Bean
    public XmlMapper xmlMapper() {
        return new XmlMapper();
    }

    /**
     * Creates and configures an ObjectMapper bean.
     * ObjectMapper is used for JSON serialization and deserialization.
     *
     * @return A configured ObjectMapper instance
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
