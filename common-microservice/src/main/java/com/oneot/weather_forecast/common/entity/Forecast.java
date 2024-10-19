package com.oneot.weather_forecast.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a complete weather forecast for a specific date.
 * This entity encapsulates all weather-related information for a given day,
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
    private String date;

    /**
     * The daytime weather forecast.
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Day day;

    /**
     * The nighttime weather forecast.
     */
    @OneToOne(cascade = CascadeType.ALL)
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
