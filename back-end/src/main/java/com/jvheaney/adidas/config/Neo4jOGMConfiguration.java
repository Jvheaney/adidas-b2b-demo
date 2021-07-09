package com.jvheaney.adidas.config;

import org.neo4j.ogm.config.Configuration.Builder;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Neo4jOGMConfiguration {
	@Bean
	public org.neo4j.ogm.config.Configuration ogmConfiguration(Neo4jProperties properties) {
		return new Builder().uri(properties.getUri())
			.credentials(properties.getUsername(), properties.getPassword())
			.database("adidas") //Set your database instance name here
			.verifyConnection(true)
			.build();
	}
}
