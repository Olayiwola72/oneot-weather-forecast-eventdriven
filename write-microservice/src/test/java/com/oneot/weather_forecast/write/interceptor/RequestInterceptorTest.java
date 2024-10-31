package com.oneot.weather_forecast.write.interceptor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mock.http.client.MockClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RequestInterceptorTest {

    private RequestInterceptor requestInterceptor;
    private ClientHttpRequestExecution execution;
    private HttpRequest request;

    @BeforeEach
    void setUp() {
        requestInterceptor = new RequestInterceptor();
        execution = mock(ClientHttpRequestExecution.class);
        request = mock(HttpRequest.class);
    }

    @Test
    void intercept_logsRequestAndResponse() throws IOException {
        // Prepare mock data
        String responseBody = "<xml>Response Body</xml>";
        byte[] requestBody = "Request Body".getBytes(StandardCharsets.UTF_8);

        // Configure mocks
        when(request.getMethod()).thenReturn(HttpMethod.GET);
        when(request.getURI()).thenReturn(URI.create("https://api.weather.com/data"));
        when(request.getHeaders()).thenReturn(new HttpHeaders());

        // Mocking response to simulate HTTP response
        MockClientHttpResponse mockResponse = new MockClientHttpResponse(
                new ByteArrayInputStream(responseBody.getBytes(StandardCharsets.UTF_8)),
                HttpStatus.OK
        );
        mockResponse.getHeaders().add("Content-Type", "application/xml");
        when(execution.execute(request, requestBody)).thenReturn(mockResponse);

        // Execute the interceptor
        ClientHttpResponse result = requestInterceptor.intercept(request, requestBody, execution);

        // Assertions and Verifications
        assertNotNull(result);
        verify(request, times(1)).getMethod();
        verify(request, times(1)).getURI();
        verify(request, times(1)).getHeaders();

        verify(execution, times(1)).execute(request, requestBody);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
