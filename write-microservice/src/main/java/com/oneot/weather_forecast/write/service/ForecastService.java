package com.oneot.weather_forecast.write.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.oneot.weather_forecast.common.repository.ForecastRepository;
import com.oneot.weather_forecast.write.config.WeatherApiProperties;
import com.oneot.weather_forecast.write.dto.ForecastResponse;
import jakarta.transaction.Transactional;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

@Service
@Transactional
public class ForecastService {

    private static final Logger logger = LoggerFactory.getLogger(ForecastService.class);

    private final ForecastRepository forecastRepository;
    private final WeatherApiProperties weatherApiProperties;
    private final RestTemplate restTemplate;
    private final MessageSource messageSource;

    public ForecastService(
            ForecastRepository forecastRepository,
            WeatherApiProperties weatherApiProperties,
            RestTemplate restTemplate,
            MessageSource messageSource
    ){
        this.forecastRepository = forecastRepository;
        this.weatherApiProperties = weatherApiProperties;
        this.restTemplate = restTemplate;
        this.messageSource = messageSource;
    }

    private String getApiUrl() {
        return weatherApiProperties.url() + "?lang=" + weatherApiProperties.lang();
    }

    private Optional<ForecastResponse> getWeatherForecast() {
        // Make a GET request to the API
        String xmlResponse = restTemplate.getForObject(getApiUrl(), String.class);

        try {
            // Use XmlMapper to convert XML to Java object
            XmlMapper xmlMapper = new XmlMapper();
            return Optional.of(
                    xmlMapper.readValue(xmlResponse, ForecastResponse.class)
            );

        } catch (Exception e) {
            String errorMessage = messageSource.getMessage("error.parse.weather.forecast", null, LocaleContextHolder.getLocale());
            logger.error(errorMessage, e);
            return Optional.empty();
        }
    }

    @Scheduled(fixedRateString = "${retry.interval}", initialDelayString = "${retry.initialDelay}")
    public void saveWeatherForcasts(){
        Optional<ForecastResponse> forecastResponseOptional = getWeatherForecast();
        forecastResponseOptional.ifPresent(forecastResponse -> forecastRepository.saveAll(forecastResponse.forecasts()));
    }

}
