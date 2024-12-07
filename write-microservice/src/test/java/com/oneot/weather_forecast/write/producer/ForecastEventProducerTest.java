package com.oneot.weather_forecast.write.producer;

import com.oneot.weather_forecast.common.enums.EventType;
import com.oneot.weather_forecast.common.event.ForecastEvent;
import com.oneot.weather_forecast.common.model.Forecast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;

public class ForecastEventProducerTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private ForecastEventProducer forecastEventProducer;

    private final String topic = "forecast_topic"; // Use the same topic you configured

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Set the topic directly for testing purposes
        forecastEventProducer = new ForecastEventProducer(kafkaTemplate, topic);
    }

    @Test
    public void testSendForecast() {
        // Arrange
        Forecast forecast = new Forecast(); // Create a sample forecast object
        forecast.setId(1L);
        ForecastEvent forecastEvent = new ForecastEvent(EventType.FORECAST_UPDATED, forecast);

        // Act
        forecastEventProducer.sendForecastEvent(forecastEvent);

        // Assert
        verify(kafkaTemplate).send(topic, forecast.getId().toString(), forecastEvent); // Verify that the send method was called with the correct parameters
    }
}
