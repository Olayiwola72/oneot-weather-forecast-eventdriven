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
public class DayResponse {

    /**
     * The weather phenomenon during the day (e.g., "Sunny", "Cloudy", "Rainy").
     */
    @Schema(description = "The weather phenomenon during the day", example = "Sunny", required = true)
    private String phenomenon;

    /**
     * The minimum temperature expected during the day.
     */
    @Schema(description = "Minimum temperature expected during the day", example = "15")
    private Integer tempMin;

    /**
     * The maximum temperature expected during the day.
     */
    @Schema(description = "Maximum temperature expected during the day", example = "25")
    private Integer tempMax;

    /**
     * Additional textual description of the day weather.
     */
    @Schema(description = "Additional textual description of the day weather", example = "Clear skies throughout the day.")
    private String text;

    /**
     * List of places for which the day forecast applies.
     */
    @Schema(description = "List of places where the daytime forecast is applicable")
    private List<PlaceResponse> places;

    /**
     * Specific weather information for Peipsi during the day, if applicable.
     */
    @Schema(description = "Specific weather information for Peipsi", example = "Slight winds in the afternoon.")
    private String peipsi;

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
