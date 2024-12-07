package com.oneot.weather_forecast.query.dto.response;

import com.oneot.weather_forecast.common.model.Day;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) for the daytime weather forecast.
 * This class represents the weather conditions during the day period.
 */
@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Represents the daytime weather forecast with detailed weather attributes.")
public class DayResponse extends WeatherPeriodResponse {

    /**
     * Specific weather information for Peipsi during the day, if applicable.
     */
    @Schema(description = "Specific weather information for Peipsi", example = "Slight winds in the afternoon.")
    private String peipsi;

    public DayResponse(String phenomenon, Integer tempMin, Integer tempMax, String text, List<PlaceResponse> places, String peipsi) {
        super(phenomenon, tempMin, tempMax, text, places);
        this.peipsi = peipsi;
    }

    /**
     * Constructs a DayResponse from the Day model.
     *
     * @param day The Day model to map.
     * @return A new instance of DayResponse populated with the Day model data.
     */
    public static DayResponse fromEntity(Day day) {
        return new DayResponse(
                day.getPhenomenon(),
                day.getTempMin(),
                day.getTempMax(),
                day.getText(),
                PlaceResponse.fromEntities(day.getPlaces()),  // Converting List of Place to List of PlaceResponse
                day.getPeipsi()
        );
    }
}
