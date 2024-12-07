package com.oneot.weather_forecast.write;

 import com.oneot.weather_forecast.write.config.WeatherApiProperties;
 import org.springframework.boot.SpringApplication;
 import org.springframework.boot.autoconfigure.SpringBootApplication;
 import org.springframework.boot.autoconfigure.domain.EntityScan;
 import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        WeatherApiProperties.class
})
@EntityScan("com.oneot.weather_forecast.common.model")
 public class ForecastWriteSchedulerApplication {

 	public static void main(String[] args) {
 		SpringApplication.run(ForecastWriteSchedulerApplication.class, args);
 	}

 }
