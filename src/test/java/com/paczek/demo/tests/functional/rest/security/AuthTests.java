package com.paczek.demo.tests.functional.rest.security;

import com.paczek.demo.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import static io.restassured.RestAssured.given;

@Epic("Practice")
@Story("Security")
public class AuthTests extends BaseTest {

    @Value("${username}")
    private String userName;
    @Value("${password}")
    private String password;
    @Value("${admin.username}")
    private String adminUserName;
    @Value("${admin.password}")
    private String adminPassword;

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
                .basic(userName, password)
                .get("secured/basic")
                .then()
                .statusCode(200);

        given()
                .auth()
                .basic(adminUserName, adminPassword)
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
                .formParam("username", adminUserName)
                .formParam("password", adminPassword)
                .post("/login")
                .then()
                .header("Location", Matchers.equalTo(baseUrl));
    }
}
