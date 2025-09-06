package com.paczek.demoRest;

import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.DOUBLE;

@SpringBootTest
public class BaseTest {

    @Value("${baseUrl}")
    protected String baseUrl;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = baseUrl;
        RestAssured.filters(new ResponseLoggingFilter(Matchers.greaterThanOrEqualTo(300)));
        RestAssured.requestSpecification = given()
                .accept(ContentType.JSON)
                .config(RestAssured.config().jsonConfig(new JsonConfig().numberReturnType(DOUBLE)));
    }

}
