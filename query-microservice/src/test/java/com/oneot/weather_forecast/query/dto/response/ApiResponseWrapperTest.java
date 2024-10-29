package com.oneot.weather_forecast.query.dto.response;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiResponseWrapperTest {

    @Test
    void testSuccess() {
        String message = "Operation successful";
        String data = "Sample data";

        ApiResponseWrapper<String> response = ApiResponseWrapper.success(message, data);

        assertEquals(200, response.status());
        assertEquals(message, response.message());
        assertEquals(data, response.data());
    }

    @Test
    void testCustom() {
        int status = 404;
        String message = "Not Found";
        String data = null;

        ApiResponseWrapper<String> response = ApiResponseWrapper.custom(status, message, data);

        assertEquals(status, response.status());
        assertEquals(message, response.message());
        assertEquals(data, response.data());
    }

}