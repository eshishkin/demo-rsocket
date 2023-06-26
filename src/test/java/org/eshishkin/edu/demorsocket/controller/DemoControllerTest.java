package org.eshishkin.edu.demorsocket.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static io.restassured.module.webtestclient.RestAssuredWebTestClient.given;
import static org.hamcrest.core.IsEqual.equalTo;


public class DemoControllerTest extends BaseIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void test_import() {
        stubFor(WireMock
                .get(anyUrl()).willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/xml")
                        .withBodyFile("mappings/currencies.xml"))
        );

        given()
            .webTestClient(webTestClient)
            .header("Accept", "application/json")
            .log().all()
        .when()
            .get("/import?currency={id}&from={from}&to={to}", "R01235", "2023-01-01", "2023-01-31")
        .then()
            .assertThat().log().all()
                .statusCode(200)
                .body("imported", equalTo(5));
    }
}
