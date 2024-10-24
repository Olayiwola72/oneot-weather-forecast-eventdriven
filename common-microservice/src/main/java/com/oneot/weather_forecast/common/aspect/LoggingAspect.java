package com.oneot.weather_forecast.common.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging errors in the application.
 */
@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Log exceptions thrown by any method in the com.oneot.weather_forecast package and its sub-packages.
    @AfterThrowing(pointcut = "execution(* com.oneot.weather_forecast.write.service..*(..))", throwing = "exception")
    public void logAfterThrowing(Exception exception) {
        logger.error("An error occurred: ", exception); // Log the exception
    }
    
}