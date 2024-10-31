package com.oneot.weather_forecast.write.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * RequestInterceptor is a Spring component that intercepts HTTP requests and responses.
 * It logs the details of the requests and responses for monitoring and debugging purposes.
 */
public class RequestInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    @NonNull
    public ClientHttpResponse intercept(@NonNull HttpRequest request, @NonNull byte[] body, @NonNull ClientHttpRequestExecution execution) throws IOException {
        // Log request details
        logRequest(request, body);

        // Execute the request
        ClientHttpResponse response = execution.execute(request, body);

        // Log response details
        logResponse(response);

        return response;
    }

    /**
     * Logs the HTTP request details.
     *
     * @param request The HttpRequest being logged.
     * @param body The request body as a byte array.
     */
    private void logRequest(HttpRequest request, byte[] body) {
        logger.info("Request Method: {}", request.getMethod());
        logger.info("Request URI: {}", request.getURI());
        logger.info("Request Headers: {}", request.getHeaders());
        logger.info("Request Body: {}", new String(body, StandardCharsets.UTF_8));
    }

    /**
     * Logs the HTTP response details.
     *
     * @param response The ClientHttpResponse being logged.
     * @throws IOException if an error occurs while logging
     */
    private void logResponse(ClientHttpResponse response) throws IOException {
        logger.info("Response Status Code: {}", response.getStatusCode());
        logger.info("Response Headers: {}", response.getHeaders());
        logger.info("Response Body: {}", StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8));
    }

}
