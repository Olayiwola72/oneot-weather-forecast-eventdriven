package com.oneot.weather_forecast.query.consumer;

import com.oneot.weather_forecast.common.event.ForecastEvent;
import com.oneot.weather_forecast.query.mapper.ForecastMapper;
import com.oneot.weather_forecast.query.model.QueryForecast;
import com.oneot.weather_forecast.query.repository.ForecastRepository;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Service;

/**
 * Service responsible for listening to forecast events from a Kafka topic.
 * This service consumes forecast data published to the Kafka topic and
 * saves it to the database using the ForecastRepository.
 */
@Service
public class ForecastEventConsumer {

    private final ForecastRepository forecastRepository;

    /**
     * Constructs a new ForecastEventConsumer with the specified ForecastRepository.
     *
     * @param forecastRepository Repository for storing weather forecasts
     */
    public ForecastEventConsumer(ForecastRepository forecastRepository) {
        this.forecastRepository = forecastRepository;
    }

    /**
     * Consumes forecast events from the specified Kafka topic.
     * This method is triggered whenever a new forecast event is published to the topic.
     *
     * @param forecastEvent The forecast event data received from the Kafka topic
     */
//    @RetryableTopic(attempts = "3")
    @KafkaListener(topics = "${kafka.consumer.topic}")
    public void consume(ForecastEvent forecastEvent) {
        // Convert commonForecast (common model) to queryForecast (query model)
        QueryForecast queryForecast = ForecastMapper.INSTANCE.toQueryForecast(forecastEvent.forecast());

        // Save to MongoDB using queryForecast model
        forecastRepository.save(queryForecast);
    }

}
