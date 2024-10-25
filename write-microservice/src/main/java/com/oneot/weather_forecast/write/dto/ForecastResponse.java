package com.oneot.weather_forecast.write.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.oneot.weather_forecast.common.model.Forecast;

import java.util.List;

/**
 * Represents the response structure for weather forecast data.
 * This class serves as a Data Transfer Object (DTO) for XML serialization.
 */
public record ForecastResponse(
        /**
         * A list of Forecast objects, each representing an individual weather forecast.
         * 
         * @JacksonXmlProperty: Sets the XML element name to "forecast" for each item in the list.
         * @JacksonXmlElementWrapper: When set to false, prevents wrapping the list in an additional XML element.
         */
        @JacksonXmlProperty(localName = "forecast")
        @JacksonXmlElementWrapper(useWrapping = false) List<Forecast> forecasts
) {
    // No additional methods or fields are required for this record class.
}
