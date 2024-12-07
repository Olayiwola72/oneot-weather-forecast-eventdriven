package com.oneot.weather_forecast.query.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "night_forecast")
@NoArgsConstructor
@Getter
@Setter
public class Night extends WeatherPeriod {

    @Id
    private String id;

    public Night(String phenomenon, int tempMin, int tempMax, String text, List<Place> places, String peipsi) {
        super(phenomenon, tempMin, tempMax, text, places, peipsi);
    }

}

