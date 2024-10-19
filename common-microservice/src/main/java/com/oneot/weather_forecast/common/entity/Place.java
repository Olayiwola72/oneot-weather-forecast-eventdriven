package com.oneot.weather_forecast.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a geographical place with weather attributes.
 * This entity extends WeatherAttributes to include location-specific information.
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
    private String name;

    /**
     * Constructs a new Place with the specified attributes.
     * 
     * @param name The name of the place.
     * @param phenomenon The weather phenomenon at this place.
     * @param tempMin The minimum temperature at this place.
     */
    public Place(String name, String phenomenon, Integer tempMin) {
        super(phenomenon, tempMin); // Call the constructor of the  WeatherAttributes superclass
        this.name = name;
    }

    /**
     * Constructs a new Place with only the name specified.
     * This constructor is useful when only the place name is known initially,
     * and other weather attributes will be set later.
     * 
     * @param name The name of the place (e.g., city, town, or region name).
     */
    public Place(String name) {
        this.name = name;
    }
}
