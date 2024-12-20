package com.oneot.weather_forecast.search.testcontainers;

import org.testcontainers.elasticsearch.ElasticsearchContainer;

public abstract class ElasticsearchTestContainer {

    private static final String ELASTICSEARCH_IMAGE = "docker.elastic.co/elasticsearch/elasticsearch:8.17.0";
    private static final ElasticsearchContainer container;

    static {
        container = new ElasticsearchContainer(ELASTICSEARCH_IMAGE)
                .withExposedPorts(9200)
                .withEnv("discovery.type", "single-node")
                .withEnv("xpack.security.enabled", "false")
                .withEnv("xpack.security.transport.ssl.enabled", "false")
                .withEnv("xpack.security.http.ssl.enabled", "false")
                .withEnv("xpack.security.http.ssl.verification_mode", "none");
        container.start();

        System.setProperty("spring.elasticsearch.uris", "http://" + container.getHost() + ":" + container.getMappedPort(9200));
    }

}

