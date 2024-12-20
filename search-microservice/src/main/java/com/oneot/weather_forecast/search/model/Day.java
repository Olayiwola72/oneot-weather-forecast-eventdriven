package com.oneot.weather_forecast.search.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Day extends WeatherPeriod {

    @Id
    private String id;

    public Day(String phenomenon, int tempMin, int tempMax, String text, List<Place> places, String peipsi) {
        super(phenomenon, tempMin, tempMax, text, places, peipsi);
    }

}

