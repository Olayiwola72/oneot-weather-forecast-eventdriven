package com.oneot.weather_forecast.search;

import com.oneot.weather_forecast.search.config.RouteConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@ComponentScan("com.oneot.weather_forecast")
@EnableConfigurationProperties({
        RouteConfig.class
})
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class SearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchServiceApplication.class, args);
    }

}

