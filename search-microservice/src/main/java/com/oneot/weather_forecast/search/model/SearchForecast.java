package com.oneot.weather_forecast.search.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oneot.weather_forecast.search.helper.Indices;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.LocalDate;

@Document(indexName = Indices.FORECAST_INDEX)
@Setting(settingPath = "static/es-settings.json")
@NoArgsConstructor
@Getter
@Setter
public class SearchForecast {

    @Id
    private String id;

    /**
     * Date of the forecast, indexed for uniqueness.
     */
    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Field(type = FieldType.Nested, includeInParent = true) // Day forecast nested
    private Day day;

    @Field(type = FieldType.Nested, includeInParent = true) // Night forecast nested
    private Night night;

    public SearchForecast(LocalDate date, Day day, Night night) {
        this.date = date;
        this.day = day;
        this.night = night;
    }

}

