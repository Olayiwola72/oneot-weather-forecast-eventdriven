package com.oneot.weather_forecast.common.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a geographical place with weather attributes.
 * This model extends WeatherAttributes to include location-specific information.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Place extends WeatherAttributes {
    
    /**
     * Unique identifier for the place.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the place (e.g., city, town, or region).
     */
    @JacksonXmlProperty(localName = "name")
    @NotEmpty
    @Column(nullable = false)
    private String name;

    /**
     * Constructs a new Place with the specified attributes.
     * 
     * @param name The name of the place.
     * @param phenomenon The weather phenomenon at this place.
     * @param tempMin The minimum temperature at this place.
     * @param tempMax The maximum temperature expected during the night.
     */
    public Place(String name, String phenomenon, Integer tempMin, Integer tempMax) {
        super(phenomenon, tempMin, tempMax); // Call the constructor of the  WeatherAttributes superclass
        this.name = name;
    }
}
