package com.oneot.weather_forecast.search.model;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class WeatherAttributes {

    /**
     * Describes the weather phenomenon (e.g., "Sunny", "Rainy", "Few Clouds").
     */
    @Field(type = FieldType.Text)
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

