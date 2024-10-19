package com.oneot.weather_forecast.common.entity;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents the weather conditions during the day period.
 * This class extends WeatherPeriod to specifically model daytime weather attributes.
 */
@Entity
@NoArgsConstructor
public class Day extends WeatherPeriod {

    /**
     * Constructs a new Day instance with the specified weather attributes.
     *
     * @param phenomenon The weather phenomenon during the day (e.g., "Sunny", "Cloudy", "Rainy").
     * @param tempMin The minimum temperature expected during the day.
     * @param tempMax The maximum temperature expected during the day.
     * @param places The places this day forecast is for.
     * @param text Additional textual description of the day weather.
     * @param peipsi Specific weather information for Peipsi during the day, if applicable.
     */
    public Day(String phenomenon, int tempMin, int tempMax, String text, List<Place> places, Peipsi peipsi) {
        super(phenomenon, tempMin, tempMax, text, places, peipsi); // Call the constructor of the WeatherPeriod superclass
    }

}
