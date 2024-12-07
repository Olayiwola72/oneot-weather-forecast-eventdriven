package com.oneot.weather_forecast.query.controller;

import com.oneot.weather_forecast.query.model.QueryForecast;
import com.oneot.weather_forecast.query.dto.response.ApiResponseWrapper;
import com.oneot.weather_forecast.query.dto.response.ForecastResponse;
import com.oneot.weather_forecast.query.dto.response.ForecastResponseMapper;
import com.oneot.weather_forecast.query.service.ForecastService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * ForecastController handles incoming requests related to weather forecasts.
 * It provides endpoints for clients to request forecast data for the current day and specific locations.
 */
@RestController
@RequestMapping("${routes.api.v1-base-url}")
@Validated // Enables validation for method parameters
@Tag(name = "QueryForecast Controller", description = "QueryForecast Controller API") // Swagger tag for API documentation
public class ForecastController {

    private final ForecastService forecastService; // Service to handle forecast logic
    private final ForecastResponseMapper forecastResponseMapper;
    private final MessageSource messageSource;

    /**
     * Constructor for ForecastController.
     *
     * @param forecastService The service used to fetch forecast data.
     */
    public ForecastController(
            ForecastService forecastService,
            ForecastResponseMapper forecastResponseMapper,
            MessageSource messageSource
    ) {
        this.forecastService = forecastService; // Initialize the forecast service
        this.forecastResponseMapper = forecastResponseMapper;
        this.messageSource = messageSource;
    }

    /**
     * Endpoint to get all forecasts for a given location.
     *
     * @param place The location for which forecasts are requested.
     * @return A list of forecasts for the specified location.
     */
    @Operation(summary = "Get all forecasts by place")
    @ApiResponses(value = { @ApiResponse(
            responseCode = "200",
            description = "Retrieve weather forecasts for a specific place. The location is matched against the places list in the WeatherPeriod (day or night).",
            content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ForecastResponse.class))
            }),
    })
    @GetMapping(path = "${routes.api.places}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseWrapper<List<ForecastResponse>>> getAllForecastsByPlace(
            @Parameter(description = "Name of the place to retrieve forecasts for", example = "Harku")
            @RequestParam(name = "place", required = true)
            @NotBlank
            String place
    ) {
        List<QueryForecast> queryForecasts = forecastService.getAllForecastsByPlace(place);

        // Map the List<QueryForecast> to List<ForecastResponse>
        List<ForecastResponse> forecastResponses = forecastResponseMapper.toForecastResponseList(queryForecasts);

        // Retrieve the message from messages.properties
        String message = messageSource.getMessage("forecast.place.success", null, LocaleContextHolder.getLocale());

        ApiResponseWrapper<List<ForecastResponse>> response = ApiResponseWrapper.success(message, forecastResponses);
        return ResponseEntity.ok(response); // Return ResponseEntity with the mapped list
    }

    /**
     * Endpoint to get today's forecasts for all locations.
     *
     * @return A list of today's forecasts in JSON format.
     */
    @Operation(summary = "Endpoint to get today's forecasts for all locations")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Endpoint to get today's forecasts for all locations", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ForecastResponse.class)) }),
    })
    @GetMapping(path = "${routes.api.today}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseWrapper<ForecastResponse>> getTodayForecast() {
        Optional<QueryForecast> forecastOptional = forecastService.getTodayForecast(); // Fetch today's forecast using the service
        ForecastResponse forecastResponse = forecastOptional
                .map(forecastResponseMapper::toForecastResponse)
                .orElse(null);

        // Retrieve the message from messages.properties
        String message = messageSource.getMessage("forecast.retrieved.success", null, LocaleContextHolder.getLocale());

        ApiResponseWrapper<ForecastResponse> response = ApiResponseWrapper.success(message, forecastResponse);
        return ResponseEntity.ok(response);
    }

}