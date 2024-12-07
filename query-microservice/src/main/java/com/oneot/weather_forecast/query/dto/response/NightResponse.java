package com.oneot.weather_forecast.query.dto.response;

import com.oneot.weather_forecast.common.model.Night;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) for the nighttime weather forecast.
 * This class represents the weather conditions during the night period.
 */
@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Represents the nighttime weather forecast with detailed weather attributes.")
public class NightResponse extends WeatherPeriodResponse {

    /**
     * Specific weather information for Peipsi during the night, if applicable.
     */
    @Schema(description = "Specific weather information for Peipsi", example = "Calm waters.")
    private String peipsi;

    public NightResponse(String phenomenon, Integer tempMin, Integer tempMax, String text, List<PlaceResponse> places, String peipsi) {
        super(phenomenon, tempMin, tempMax, text, places);
        this.peipsi = peipsi;
    }
    
    /**
     * Constructs a NightResponse from the Night model.
     *
     * @param night The Night model to map.
     * @return A new instance of NightResponse populated with the Night model data.
     */
    public static NightResponse fromEntity(Night night) {
        return new NightResponse(
                night.getPhenomenon(),
                night.getTempMin(),
                night.getTempMax(),
                night.getText(),
                PlaceResponse.fromEntities(night.getPlaces()),  // Converting List of Place to List of PlaceResponse
                night.getPeipsi()
        );
    }
}

