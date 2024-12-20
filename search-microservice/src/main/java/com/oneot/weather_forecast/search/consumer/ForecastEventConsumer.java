package com.oneot.weather_forecast.search.consumer;

import com.oneot.weather_forecast.common.event.ForecastEvent;
import com.oneot.weather_forecast.search.mapper.ForecastMapper;
import com.oneot.weather_forecast.search.model.SearchForecast;
import com.oneot.weather_forecast.search.repository.ForecastRepository;

import org.springframework.kafka.annotation.KafkaListener;
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
        // Convert commonForecast (common model) to searchForecast (search model)
        SearchForecast searchForecast = ForecastMapper.INSTANCE.toSearchForecast(forecastEvent.forecast());

        // Save to ElasticSearch using searchForecast model
        forecastRepository.save(searchForecast);
    }
}
