package com.oneot.weather_forecast.query.testcontainers;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

public abstract class MongoTestContainer {

    private static final String MONGO_IMAGE = "mongo:7.0";
    private static MongoDBContainer container;

    @DynamicPropertySource
    static void containersProperties(DynamicPropertyRegistry registry) {
        container = MongoDBContainerHolder.INSTANCE;
        registry.add("spring.data.mongodb.host", container::getHost);
        registry.add("spring.data.mongodb.port", () -> container.getFirstMappedPort());
        registry.add("spring.data.mongodb.auto-index-creation", () -> true);
    }

    private static class MongoDBContainerHolder {
        private static final MongoDBContainer INSTANCE = createMongoDBContainer();

        private static MongoDBContainer createMongoDBContainer() {
            MongoDBContainer container = new MongoDBContainer(MONGO_IMAGE)
                    .withExposedPorts(27017);
            container.start();

            return container;
        }
    }

}
