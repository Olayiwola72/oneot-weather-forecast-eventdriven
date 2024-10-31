package com.oneot.weather_forecast.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * LoggingAspect is an aspect for logging method execution details, including
 * successful responses and exceptions for methods annotated with @GetMapping.
 * It helps in monitoring the application's behavior and troubleshooting issues.
 */
@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /** 
    * Pointcut for any method annotated with @GetMapping
    */
    @Before("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Executing @GetMapping method: {}", joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        if (args != null) {
            for (Object arg : args) {
                logger.info("Argument: {}", arg);
            }
        }
    }

    /**
    * Log successful response after @GetMapping method returns
    */
    @AfterReturning(pointcut = "@annotation(org.springframework.web.bind.annotation.GetMapping)", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, ResponseEntity<?> result) {
        logger.info("Completed @GetMapping method: {}", joinPoint.getSignature().getName());
        logger.info("Response Status Code: {}", result.getStatusCode());
        logger.info("Response Body: {}", result.getBody());
    }

    /** 
    * Log exceptions thrown by @GetMapping methods
    */
    @AfterThrowing(pointcut = "@annotation(org.springframework.web.bind.annotation.GetMapping)", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        logger.error("Exception in @GetMapping method: {}. Message: {}", joinPoint.getSignature().getName(), ex.getMessage(), ex);
    }

    /** 
    * Log exceptions thrown by any method in the com.oneot.weather_forecast.write.service package and its sub-packages.
    */
    @AfterThrowing(pointcut = "execution(* com.oneot.weather_forecast.write.service..*(..))", throwing = "exception")
    public void logAfterThrowing(Exception exception) {
        logger.error("An error occurred: ", exception); // Log the exception
    }
    
}