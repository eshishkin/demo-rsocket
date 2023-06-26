package org.eshishkin.edu.demorsocket.controller;

import io.restassured.RestAssured;
import org.eshishkin.edu.demorsocket.persistence.CurrencyRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureWebClient
@AutoConfigureWireMock(port = 0)
public class BaseIntegrationTest {
    @Container
    private final static MongoDBContainer MONGO = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
    @LocalServerPort
    private int port;

    @Autowired
    private CurrencyRateRepository repository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.host", MONGO::getHost);
        registry.add("spring.data.mongodb.port", () -> MONGO.getMappedPort(27017));
    }

    @BeforeEach
    public void before() {
        RestAssured.port = port;

        repository.deleteAll();
    }
}
