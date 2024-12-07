package com.oneot.weather_forecast.query.model;

import lombok.*;

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
    private Integer tempMin;

    /**
     * Represents the maximum temperature.
     */
    private Integer tempMax;
}

