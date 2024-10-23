package com.oneot.weather_forecast.query.controller;

import com.oneot.weather_forecast.common.entity.Forecast;
import com.oneot.weather_forecast.query.dto.request.ForecastRequest;
import com.oneot.weather_forecast.query.dto.response.ForecastResponse;
import com.oneot.weather_forecast.query.dto.response.ForecastResponseMapper;
import com.oneot.weather_forecast.query.service.ForecastService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ForecastController handles incoming requests related to weather forecasts.
 * It provides endpoints for clients to request forecast data for the current day and specific locations.
 */
@RestController
@RequestMapping("${routes.api.base}") // Base URL for the API
@Validated // Enables validation for method parameters
@Tag(name = "Forecast Controller", description = "Forecast Controller API") // Swagger tag for API documentation
public class ForecastController {

    private final ForecastService forecastService; // Service to handle forecast logic
    private final ForecastResponseMapper forecastResponseMapper;

    /**
     * Constructor for ForecastController.
     *
     * @param forecastService The service used to fetch forecast data.
     */
    public ForecastController(
            ForecastService forecastService,
            ForecastResponseMapper forecastResponseMapper
    ) {
        this.forecastService = forecastService; // Initialize the forecast service
        this.forecastResponseMapper = forecastResponseMapper;
    }

    /**
     * Endpoint to get all forecasts for a given location.
     *
     * @param request The request containing the location for which forecasts are requested.
     * @return A list of forecasts for the specified location.
     */
    @Operation(summary = "Endpoint to get all forecasts for a given location")
    @ApiResponses(value = { @ApiResponse(
            responseCode = "200",
            description = "Endpoint to get all forecasts for a given location. The location is matched against the places list in the WeatherPeriod (day or night).",
            content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Forecast.class))
            }),
    })
    @GetMapping(path = "${routes.api.places}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ForecastResponse>> getAllForecastsByPlace(
            @Valid @RequestBody ForecastRequest request // Validates the request body
    ) {
        List<Forecast> forecasts = forecastService.getAllForecastsByPlace(request.place()); // Fetch forecasts using the service

        // Map the List<Forecast> to List<ForecastResponse>
        List<ForecastResponse> forecastResponses = forecastResponseMapper.toForecastResponseList(forecasts);

        return ResponseEntity.ok(forecastResponses); // Return ResponseEntity with the mapped list
    }

    /**
     * Endpoint to get today's forecasts for all locations.
     *
     * @return A list of today's forecasts in JSON format.
     */
    @Operation(summary = "Endpoint to get today's forecasts for all locations")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Endpoint to get today's forecasts for all locations", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Forecast.class)) }),
    })
    @GetMapping(path = "${routes.api.today}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Forecast> getTodayForecasts() {
        return forecastService.getTodayForecasts(); // Fetch today's forecasts using the service
    }
    
}