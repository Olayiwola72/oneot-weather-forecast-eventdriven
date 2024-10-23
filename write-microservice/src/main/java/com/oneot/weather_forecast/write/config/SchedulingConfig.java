package com.oneot.weather_forecast.write.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configuration class for enabling scheduling in the application.
 * This class uses Spring's @EnableScheduling annotation to allow
 * the use of @Scheduled annotations on methods throughout the application.
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {
	
    // This class is intentionally left empty.
    // The @EnableScheduling annotation is sufficient to enable scheduling.
    // Actual scheduled tasks are defined in other components using @Scheduled annotations. e.g. ForecastService.java
}
