package com.oneot.weather_forecast.query.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "forecasts")
@NoArgsConstructor
@Getter
@Setter
public class QueryForecast {

    @Id
    private String id;

    @Field("date")
    @Indexed(unique = true) // Index to the "date" field to optimize date-based searches. It is made unique to avoid duplicate forecasts for the same date
    private String date;

    private Day day;

    private Night night;

    public QueryForecast(String date, Day day, Night night) {
        this.date = date;
        this.day = day;
        this.night = night;
    }

}

