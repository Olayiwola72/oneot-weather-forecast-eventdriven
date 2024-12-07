package com.oneot.weather_forecast.write.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for setting up Kafka producer settings and creating KafkaTemplate.
 * This class defines the necessary beans for producing messages to Kafka topics.
 */
@Configuration
public class KafkaProducerConfig {

    @Value("${kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.producer.topic}")
    private String topic;

    @Value("${kafka.producer.partitions}")
    private Integer numPartitions;

    @Value("${kafka.producer.replication-factor}")
    private short replicationFactor;

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
     * Creates a new Kafka topic with the specified name, number of partitions, and replication factor.
     *
     * @return NewTopic The newly created Kafka topic
     */
    @Bean
    public NewTopic createTopic() {
        return new NewTopic(topic, numPartitions, replicationFactor);
    }

    /**
     * Configures the producer settings for Kafka.
     * This method defines the necessary properties for the Kafka producer, including
     * the bootstrap servers and serializers for keys and values.
     *
     * @return Map<String, Object> A map containing the producer configuration settings
     */
    @Bean
    public Map<String, Object> producerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // SSL configurations
        config.put("security.protocol", securityProtocol);
        config.put("ssl.keystore.location", keystoreLocation);
        config.put("ssl.keystore.password", keystorePassword);
        config.put("ssl.key.password", keyPassword);
        config.put("ssl.truststore.location", truststoreLocation);
        config.put("ssl.truststore.password", truststorePassword);

        return config;
    }

    /**
     * Creates a ProducerFactory for producing messages to Kafka.
     * This factory uses the producer configuration defined in the producerConfig method.
     *
     * @return ProducerFactory<String, Object> The producer factory for creating Kafka producers
     */
    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    /**
     * Creates a KafkaTemplate for sending messages to Kafka topics.
     * This template uses the ProducerFactory defined in the producerFactory method.
     *
     * @return KafkaTemplate<String, Object> The Kafka template for sending messages
     */
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
