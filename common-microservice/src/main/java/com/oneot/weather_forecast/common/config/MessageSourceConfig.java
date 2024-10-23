package com.oneot.weather_forecast.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Configuration class for setting up the MessageSource.
 * This class provides a custom configuration for internationalization (i18n) support in the application.
 */
@Configuration
public class MessageSourceConfig {
	
	/**
	 * Creates and configures a ReloadableResourceBundleMessageSource bean.
	 * This bean is used for loading and managing internationalized messages.
	 *
	 * @return A configured ReloadableResourceBundleMessageSource instance
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        
        // Set the base name for the messages properties files
        messageSource.setBasename("classpath:messages");
        
        // Set the default encoding for reading the properties files
        messageSource.setDefaultEncoding("UTF-8");
        
        // Set the cache refresh interval (in seconds)
        // This allows for reloading of the message files every hour without restarting the application
        messageSource.setCacheSeconds(3600);
        
        return messageSource;
    }
}
