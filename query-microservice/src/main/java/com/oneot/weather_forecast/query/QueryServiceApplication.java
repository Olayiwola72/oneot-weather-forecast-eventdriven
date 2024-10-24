package com.oneot.weather_forecast.query;

 import com.oneot.weather_forecast.query.config.RouteConfig;
 import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 import org.springframework.boot.context.properties.EnableConfigurationProperties;
 import org.springframework.context.annotation.ComponentScan;
 import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.oneot.weather_forecast")
@EnableJpaRepositories("com.oneot.weather_forecast.common.repository")
@EnableConfigurationProperties({
        RouteConfig.class
})
 public class QueryServiceApplication {

 	public static void main(String[] args) {
 		SpringApplication.run(QueryServiceApplication.class, args);
 	}

 }