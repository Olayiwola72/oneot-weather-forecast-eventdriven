package com.oneot.weather_forecast.common.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Represents the weather conditions during the night period.
 * This class extends WeatherPeriod to specifically model nighttime weather attributes.
 */
@Entity
@NoArgsConstructor
@Table(name = "night_forecast") // Change the table name to avoid conflict
@Getter
@Setter
public class Night extends WeatherPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Constructs a new Night instance with the specified weather attributes.
     *
     * @param phenomenon The weather phenomenon during the night (e.g., "Clear", "Cloudy", "Rainy").
     * @param tempMin The minimum temperature expected during the night.
     * @param tempMax The maximum temperature expected during the night.
     * @param places The specific places this night forecast is for.
     * @param text Additional textual description of the night weather.
     * @param peipsi Specific weather information for Peipsi during the night, if applicable.
     */
    public Night(String phenomenon, int tempMin, int tempMax, String text, List<Place> places, String peipsi) {
        super(phenomenon, tempMin, tempMax, text, places, peipsi); // Call the constructor of the superclass
    }

}
