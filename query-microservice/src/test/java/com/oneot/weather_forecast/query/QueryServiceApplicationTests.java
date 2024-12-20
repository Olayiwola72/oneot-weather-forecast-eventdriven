package com.oneot.weather_forecast.query;

import com.oneot.weather_forecast.query.testcontainers.MongoTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"test"}) // 'test' profile has higher priority over 'dev'
class QueryServiceApplicationTests extends MongoTestContainer {

	@Test
	void contextLoads() {
	}

}
