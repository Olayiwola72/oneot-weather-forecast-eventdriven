package com.oneot.weather_forecast.search.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;

@Configuration
@EnableElasticsearchRepositories
public class ElasticsearchConfig {

    @Value("${elasticsearch.host}")
    private String hostname;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.scheme}")
    private String scheme;

    @Value("${elasticsearch.username}")
    private String username;

    @Value("${elasticsearch.keystore.type}")
    private String keystoreType;

    @Value("${elasticsearch.password}")
    private String password;

    @Value("file:${elasticsearch.keystore.path}") // correctly interpret the absolute file path with file: prefix
    private Resource keystoreResource;

    @Value("${elasticsearch.keystore.password}")
    private String keystorePassword;

    @Value("${elasticsearch.disable.ssl.verification-mode}")
    private boolean disableSslVerificationMode;

    @Bean
    @ConditionalOnProperty(name = "elasticsearch.scheme", havingValue = "https")
    public ElasticsearchClient elasticsearchClient() throws Exception {
        // Load SSLContext from a keystore
        SSLContext sslContext = createSSLContext();

        // Set credentials
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        // Create RestClientBuilder
        RestClientBuilder builder = RestClient.builder(new HttpHost(hostname, port, scheme))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder
                                .setSSLContext(sslContext)
                                .setSSLHostnameVerifier((hostname, session) -> disableSslVerificationMode) // Disable hostname verification
                                .setDefaultCredentialsProvider(credentialsProvider));

        // Create an Elasticsearch client using the RestClientTransport
        return new ElasticsearchClient(new RestClientTransport(builder.build(), new JacksonJsonpMapper()));
    }

    /**
     * Creates SSLContext with a Keystore.
     */
    private SSLContext createSSLContext() throws Exception {
        // Load the keystore
        KeyStore keyStore = KeyStore.getInstance(keystoreType);

        // Load the keystore from the Resource's InputStream
        try (InputStream fis = keystoreResource.getInputStream()) {
            keyStore.load(
                    fis,
                    keystorePassword != null && !keystorePassword.isBlank() ? keystorePassword.toCharArray() : null
            );
        }

        // Create the SSLContext
        return SSLContextBuilder.create()
                .loadTrustMaterial(keyStore, (chain, authType) -> true)  // Trust all certificates (optional)
                .setProtocol("TLS")
                .build();
    }

}
