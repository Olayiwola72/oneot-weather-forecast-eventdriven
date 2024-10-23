package com.oneot.weather_forecast.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Represents a weather period, which could be either day or night.
 * This entity extends WeatherAttributes to include additional specific information for a time period.
 */
@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class WeatherPeriod extends WeatherAttributes  {

    /**
     * Additional textual description of the weather for this period.
     */
    @Column(length = 5000) // Increase the length for longer texts
    private String text;

    /**
     * List of place-specific forecasts.
     */
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "place")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Place> places;

    /**
     * The wind element can be ignored both for serialization/deserialization and for Hibernate.
     */
    @Transient
    @JsonIgnore
    private List<Object> wind;

    /**
     * The sea element can be ignored both for serialization/deserialization and for Hibernate.
     */
    @Transient
    @JsonIgnore
    private List<Object> sea;

    /**
     * Specific forecast for Peipsi.
     */
    @Column(length = 5000) // Increase the length for longer texts
    private String peipsi;

    /**
     * Constructs a new WeatherPeriod with the specified attributes.
     * 
     * @param phenomenon The weather phenomenon for this period.
     * @param tempMin The minimum temperature for this period.
     * @param tempMax The maximum temperature for this period.
     * @param places The places this weather applies to.
     * @param text Additional textual description of the weather.
     * @param peipsi Additional textual weather information.
     */
    public WeatherPeriod(String phenomenon, Integer tempMin, Integer tempMax, String text, List<Place> places, String peipsi){
        super(phenomenon, tempMin, tempMax); // Call the constructor of the superclass
        this.text = text;
        this.places = places;
        this.peipsi = peipsi;
    }

}
