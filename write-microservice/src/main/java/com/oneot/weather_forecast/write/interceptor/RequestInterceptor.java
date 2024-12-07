package com.oneot.weather_forecast.write.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * RequestInterceptor is a Spring component that intercepts HTTP requests and responses.
 * It logs the details of the requests and responses for monitoring and debugging purposes.
 */
@Slf4j // Lombok annotation for logging
public class RequestInterceptor implements ClientHttpRequestInterceptor {

    /**
     * Intercepts the HTTP request and response.
     *
     * @param request The HTTP request to be intercepted
     * @param body The request body as a byte array
     * @param execution The execution context for the request
     * @return ClientHttpResponse The response from the executed request
     * @throws IOException if an error occurs during request execution
     */
    @Override
    @NonNull
    public ClientHttpResponse intercept(@NonNull HttpRequest request, @NonNull byte[] body, @NonNull ClientHttpRequestExecution execution) throws IOException {
        // Log request details
        logRequest(request, body);

        // Execute the request and get the response
        ClientHttpResponse response = execution.execute(request, body);

        // Log response details
        logResponse(response);

        return response;
    }

    /**
     * Logs the HTTP request details.
     *
     * @param request The HttpRequest being logged
     * @param body The request body as a byte array
     */
    private void logRequest(HttpRequest request, byte[] body) {
        log.info("Request Method: {}", request.getMethod());
        log.info("Request URI: {}", request.getURI());
        logHeaders(request.getHeaders());
        if(body.length > 0){
            log.info("Request Body: {}", new String(body, StandardCharsets.UTF_8));
        }
    }

    /**
     * Logs the HTTP response details.
     *
     * @param response The ClientHttpResponse being logged
     * @throws IOException if an error occurs while logging
     */
    private void logResponse(ClientHttpResponse response) throws IOException {
        log.info("Response Status Code: {}", response.getStatusCode());
        logHeaders(response.getHeaders());

        byte[] responseBody = response.getBody().readAllBytes();
        if(responseBody.length > 0){
            log.info("Response Body: {}", new String(responseBody, StandardCharsets.UTF_8));
        }
    }

    /**
     * Logs the HTTP headers.
     *
     * @param headers The HttpHeaders to be logged.
     */
    private void logHeaders(HttpHeaders headers){
        if (headers != null) {
            headers.forEach((name, values) ->
                    values.forEach(value -> log.info("{}={}", name, value))
            );
        }
    }

}
