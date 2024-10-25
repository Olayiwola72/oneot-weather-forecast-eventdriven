package com.oneot.weather_forecast.common.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

/**
 * Abstract base class for weather-related entities.
 * This class defines common attributes for weather conditions.
 */
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class WeatherAttributes {

    /**
     * Describes the weather phenomenon (e.g., "Sunny", "Rainy", "Few Clouds").
     */
    private String phenomenon;

    /**
     * Represents the minimum temperature.
     */
    @JacksonXmlProperty(localName = "tempmin", isAttribute = true)
    private Integer tempMin;

    /**
     * Represents the maximum temperature.
     */
    @JacksonXmlProperty(localName = "tempmax", isAttribute = true)
    private Integer tempMax;

}
