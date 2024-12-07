package com.oneot.weather_forecast.query.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "places")
@NoArgsConstructor
@Getter
@Setter
public class Place extends WeatherAttributes {

    @Id
    private String id;

    @Field("name")
    @Indexed(direction = IndexDirection.ASCENDING) // Indexing the "name" field to optimize queries that search by place name
    private String name;

    public Place(String name, String phenomenon, Integer tempMin, Integer tempMax) {
        super(phenomenon, tempMin, tempMax);
        this.name = name;
    }

}

