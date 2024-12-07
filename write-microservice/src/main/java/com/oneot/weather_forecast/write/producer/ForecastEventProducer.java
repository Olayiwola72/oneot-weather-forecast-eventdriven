package com.oneot.weather_forecast.write.producer;

import com.oneot.weather_forecast.common.event.ForecastEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Service responsible for producing and sending forecast events to a Kafka topic.
 * This service uses KafkaTemplate to send serialized forecast data to the specified topic.
 */
@Service
public class ForecastEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate; // Kafka template for sending forecast messages
    private final String topic; // The Kafka topic to which forecasts will be sent

    /**
     * Constructs a new ForecastEventProducer with the specified KafkaTemplate and topic.
     *
     * @param kafkaTemplate KafkaTemplate used for sending messages to Kafka
     * @param topic The name of the Kafka topic to which forecast events will be sent
     */
    public ForecastEventProducer(KafkaTemplate<String, Object> kafkaTemplate,
                                 @Value("${kafka.producer.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate; // Initialize the Kafka template
        this.topic = topic; // Initialize the topic name
    }

    /**
     * Sends a forecast event to the configured Kafka topic.
     *
     * @param forecastEvent The forecast event containing forecast data to be sent as an event
     */
    public void sendForecastEvent(ForecastEvent forecastEvent) {
        kafkaTemplate.send(topic, forecastEvent.forecast().getId().toString(), forecastEvent); // Send the forecast to the specified Kafka topic
    }

}
