package com.paczek.demoRest.security;

import com.paczek.demoRest.BaseTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class AuthTests extends BaseTest {

    @Test
    void basicAuthFailed() {
        given()
                .get("secured/basic")
                .then()
                .statusCode(401);

        given()
                .auth()
                .basic("user", "wrong_password")
                .get("secured/basic")
                .then()
                .statusCode(401);
    }

    @Test
    void basicAuthOk() {
        given()
                .auth()
                .basic("user", "password")
                .get("secured/basic")
                .then()
                .statusCode(200);

        given()
                .auth()
                .basic("admin", "admin")
                .get("secured/basic")
                .then()
                .statusCode(200);
    }

    @Test
    void formFailed() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", "admin")
                .formParam("password", "admin2")
                .post("/login")
                .then()
                .header("Location", Matchers.endsWith("error"));
    }

    @Test
    void formOk() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("username", "admin")
                .formParam("password", "admin")
                .post("/login")
                .then()
                .header("Location", Matchers.equalTo(baseUrl));
    }
}
