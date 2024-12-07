package com.oneot.weather_forecast.query.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for setting up Kafka consumer settings and creating Kafka consumer containers.
 * This class defines the necessary beans for consuming messages from Kafka topics.
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.consumer.group-id}")
    private String groupId;

    @Value("${kafka.consumer.trusted-packages}")
    private String trustedPackages;

    @Value("${kafka.auto.offset.reset}")
    private String autoOffsetReset;

    @Value("${kafka.ssl.security-protocol}")
    private String securityProtocol;

    @Value("${kafka.ssl.keystore-location}")
    private String keystoreLocation;

    @Value("${kafka.ssl.keystore-password}")
    private String keystorePassword;

    @Value("${kafka.ssl.key-password}")
    private String keyPassword;

    @Value("${kafka.ssl.truststore-location}")
    private String truststoreLocation;

    @Value("${kafka.ssl.truststore-password}")
    private String truststorePassword;

    /**
     * Configures the consumer settings for Kafka.
     * This method defines the necessary properties for the Kafka consumer, including
     * the bootstrap servers, group ID, and deserializers for keys and values.
     *
     * @return Map<String, Object> A map containing the consumer configuration settings
     */
    @Bean
    public Map<String, Object> consumerConfig() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, trustedPackages);
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);         // Set the consumer to start reading from the earliest offset if no committed offset exists

        // SSL configurations
        configProps.put("security.protocol", securityProtocol);
        configProps.put("ssl.keystore.location", keystoreLocation);
        configProps.put("ssl.keystore.password", keystorePassword);
        configProps.put("ssl.key.password", keyPassword);
        configProps.put("ssl.truststore.location", truststoreLocation);
        configProps.put("ssl.truststore.password", truststorePassword);

        return configProps;
    }

    /**
     * Creates a ConsumerFactory for consuming messages from Kafka.
     * This factory uses the consumer configuration defined in the consumerConfig method.
     *
     * @return ConsumerFactory<String, Object> The consumer factory for creating Kafka consumers
     */
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    /**
     * Creates a KafkaListenerContainerFactory for creating concurrent message consumer containers.
     * This factory uses the ConsumerFactory defined in the consumerFactory method.
     *
     * @return KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> The consumer container factory for Kafka
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}

