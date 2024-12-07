package com.oneot.weather_forecast.common;

 import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.oneot.weather_forecast")
 public class CommonMicroserviceApplication {

 	public static void main(String[] args) {
 		SpringApplication.run(CommonMicroserviceApplication.class, args);
 	}

 }
