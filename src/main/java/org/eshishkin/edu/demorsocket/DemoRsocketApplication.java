package org.eshishkin.edu.demorsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.messaging.rsocket.RSocketRequester;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class DemoRsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoRsocketApplication.class, args);
	}

	@Bean
	public RSocketRequester requester(RSocketRequester.Builder builder, RSocketProperties properties) {
		return builder
				.tcp("localhost", properties.getServer().getPort());
	}
}
