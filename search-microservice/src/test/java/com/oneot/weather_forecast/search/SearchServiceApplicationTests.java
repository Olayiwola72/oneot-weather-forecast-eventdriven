package com.oneot.weather_forecast.search;

import com.oneot.weather_forecast.search.testcontainers.ElasticsearchTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = SearchServiceApplication.class)
@ActiveProfiles({"dev", "test"}) // 'test' profile has higher priority over 'dev'
class SearchServiceApplicationTests extends ElasticsearchTestContainer {

	@Test
	void contextLoads() {

	}

}
