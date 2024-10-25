package com.oneot.weather_forecast.query.dto.response;

import com.oneot.weather_forecast.common.model.Place;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data Transfer Object (DTO) for representing a geographical place.
 */
@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Represents a geographical place with weather information.")
public class PlaceResponse {

    @Schema(description = "Name of the place", example = "Tallinn")
    private String name;

    @Schema(description = "Weather phenomenon at the place", example = "Cloudy")
    private String phenomenon;

    @Schema(description = "Minimum temperature at the place", example = "-5")
    private Integer tempMin;

    @Schema(description = "Maximum temperature at the place", example = "5")
    private Integer tempMax;

    /**
     * Converts a Place model to PlaceResponse.
     *
     * @param place The Place model to convert.
     * @return A PlaceResponse DTO.
     */
    public static PlaceResponse fromEntity(Place place) {
        return new PlaceResponse(
                place.getName(),
                place.getPhenomenon(),
                place.getTempMin(),
                place.getTempMax()
        );
    }

    /**
     * Converts a list of Place entities to a list of PlaceResponse DTOs.
     *
     * @param places List of Place entities.
     * @return List of PlaceResponse DTOs.
     */
    public static List<PlaceResponse> fromEntities(List<Place> places) {
        if (places == null) {
            return Collections.emptyList(); // Return an empty list if places is null
        }

        return places.stream()
                .map(PlaceResponse::fromEntity)  // Convert each Place to PlaceResponse
                .collect(Collectors.toList());    // Collect results into a List
    }
}
