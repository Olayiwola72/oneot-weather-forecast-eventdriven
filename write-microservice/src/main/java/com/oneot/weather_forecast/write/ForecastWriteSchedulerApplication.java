package com.oneot.weather_forecast.write;

 import com.oneot.weather_forecast.write.config.WeatherApiProperties;
 import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 import org.springframework.boot.autoconfigure.domain.EntityScan;
 import org.springframework.boot.context.properties.EnableConfigurationProperties;
 import org.springframework.context.annotation.ComponentScan;
 import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.oneot.weather_forecast")
@EntityScan("com.oneot.weather_forecast.common.model")
@EnableJpaRepositories("com.oneot.weather_forecast.common.repository")
@EnableConfigurationProperties({
        WeatherApiProperties.class
})
 public class ForecastWriteSchedulerApplication {

 	public static void main(String[] args) {
 		SpringApplication.run(ForecastWriteSchedulerApplication.class, args);
 	}

 }
