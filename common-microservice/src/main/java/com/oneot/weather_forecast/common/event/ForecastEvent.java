package com.oneot.weather_forecast.common.event;

import com.oneot.weather_forecast.common.enums.EventType;
import com.oneot.weather_forecast.common.model.Forecast;

/**
 * Represents an event related to weather forecasts.
 * 
 * @param eventType The type of the event (e.g., created, updated).
 * @param forecast  The forecast associated with the event.
 */
public record ForecastEvent (
        EventType eventType,
        Forecast forecast
) {

}
