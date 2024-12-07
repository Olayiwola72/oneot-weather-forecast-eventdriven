package com.oneot.weather_forecast.query;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"dev", "test"}) // 'test' profile has higher priority over 'dev'
class QueryServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
