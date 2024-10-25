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
public class NightResponse {

    /**
     * The weather phenomenon during the night (e.g., "Clear", "Cloudy", "Rainy").
     */
    @Schema(description = "The weather phenomenon during the night", example = "Clear", required = true)
    private String phenomenon;

    /**
     * The minimum temperature expected during the night.
     */
    @Schema(description = "Minimum temperature expected during the night", example = "-5")
    private Integer tempMin;

    /**
     * The maximum temperature expected during the night.
     */
    @Schema(description = "Maximum temperature expected during the night", example = "5")
    private Integer tempMax;

    /**
     * Additional textual description of the night weather.
     */
    @Schema(description = "Additional textual description of the night weather", example = "Clear skies with a slight breeze.")
    private String text;

    /**
     * List of places for which the night forecast applies.
     */
    @Schema(description = "List of places where the nighttime forecast is applicable")
    private List<PlaceResponse> places;

    /**
     * Specific weather information for Peipsi during the night, if applicable.
     */
    @Schema(description = "Specific weather information for Peipsi", example = "Calm waters.")
    private String peipsi;

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

