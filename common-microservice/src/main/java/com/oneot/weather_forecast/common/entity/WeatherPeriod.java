package com.oneot.weather_forecast.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Entity
@NoArgsConstructor
@Getter
@Setter
public class WeatherPeriod extends WeatherAttributes  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Inherited from WeatherAttributes:
    // private String phenomenon;
    // private Integer tempMin;

    /**
     * Maximum temperature for this period in degrees Celsius.
     */
    @JacksonXmlProperty(localName = "tempmax", isAttribute = true)
    private Integer tempMax;

    /**
     * Additional textual description of the weather for this period.
     */
    private String text;

    /**
     * List of place-specific forecasts.
     */
    @JacksonXmlProperty(localName = "place", isAttribute = true)
    @OneToMany(cascade = CascadeType.ALL)
    private List<Place> places;

    /**
     * The wind element can be ignored both for serialization/deserialization and for Hibernate.
     */
    @Transient
    @JsonIgnore // This can be optional if you already have @Transient
    private List<Object> wind; // Assuming you have a Wind class that represents wind attributes


    /**
     * The sea element can be ignored both for serialization/deserialization and for Hibernate.
     */
    @Transient
    @JsonIgnore // This can be optional if you already have @Transient
    private List<Object> sea; // Assuming you have a Wind class that represents wind attributes

    /**
     * Specific forecast for Peipsi.
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Peipsi peipsi;

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
    public WeatherPeriod(String phenomenon, Integer tempMin, Integer tempMax, String text, List<Place> places, Peipsi peipsi){
        super(phenomenon, tempMin); // Call the constructor of the superclass
        this.tempMax = tempMax;
        this.text = text;
        this.places = places;
        this.peipsi = peipsi;
    }
}
