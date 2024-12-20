package com.oneot.weather_forecast.search.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@NoArgsConstructor
@Getter
@Setter
public class Place extends WeatherAttributes {

    @Id
    private String id;

    /**
     * Name of the place, indexed for search.
     */
    @Field(type = FieldType.Text, analyzer = "standard")
//    @Field(type = FieldType.Keyword)
    private String name;

    public Place(String name, String phenomenon, Integer tempMin, Integer tempMax) {
        super(phenomenon, tempMin, tempMax);
        this.name = name;
    }

}

