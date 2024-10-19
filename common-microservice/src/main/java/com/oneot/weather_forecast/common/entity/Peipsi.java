package com.oneot.weather_forecast.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the weather forecast for Peipsi.
 * This entity contains specific weather information for Peipsi.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Peipsi{

    /**
     * Unique identifier for the Peipsi weather forecast.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Textual description of the weather forecast for Peipsi.
     * This may include information about wind conditions, wave height, and other relevant details.
     */
    private String forecastText;

    /**
     * Constructs a new Peipsi with the specified forecast text.
     * 
     * @param forecastText The textual description of the weather forecast.
     */
    public Peipsi(String forecastText){
        this.forecastText = forecastText;
    }
}
