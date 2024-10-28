package com.oneot.weather_forecast.common.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a complete weather forecast for a specific date.
 * This model encapsulates all weather-related information for a given day,
 * including day and night forecasts, specific place forecasts, and Peipsi.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Forecast {
    /**
     * Unique identifier for the forecast.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The date for which this forecast is valid.
     */
    @NotBlank
    @Column(unique = true, nullable = false, length = 10)
    private String date;

    /**
     * The daytime weather forecast.
     * orphanRemoval = true - delete day if forecast is deleted or if the reference is removed
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Day day;

    /**
     * The nighttime weather forecast.
     * orphanRemoval = true - delete night if forecast is deleted or if the reference is removed
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) // 
    private Night night;

    /**
     * Constructs a new Forecast with all attributes.
     *
     * @param date The date of the forecast.
     * @param day The daytime forecast.
     * @param night The nighttime forecast.
     */
    public Forecast(String date, Day day, Night night) {
        this.date = date;
        this.day = day;
        this.night = night;
    }
}
