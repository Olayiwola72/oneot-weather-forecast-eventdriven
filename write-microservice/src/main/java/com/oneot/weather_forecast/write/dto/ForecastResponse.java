package com.oneot.weather_forecast.write.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.oneot.weather_forecast.common.entity.Forecast;

import java.util.List;

public record ForecastResponse(
        @JacksonXmlProperty(localName = "forecast")
        @JacksonXmlElementWrapper(useWrapping = false) List<Forecast> forecasts
) {

}