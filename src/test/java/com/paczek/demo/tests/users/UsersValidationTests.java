package com.paczek.demo.tests.users;

import com.paczek.demo.app.users.Gender;
import com.paczek.demo.app.users.UserDto;
import com.paczek.demo.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

@Epic("App - users")
@Story("Create user")
public class UsersValidationTests extends BaseTest {

    @Test
    void cannotCreateUserWithNullProperties(){
        final UserDto userToCreate = new UserDto(null, null,null,null,null,null);
        given()
                .contentType(ContentType.JSON)
                .body(userToCreate)
                .post("users")
                .then()
                .statusCode(400);
    }

    @Test
    void canFilterUsers() {

        final String gender = Gender.Male.name();
        final String emailSuffix = "pl";
        List<UserDto> users = given()
                .queryParam("email", emailSuffix)
                .queryParam("gender", gender)
                .get("users")
                .then()
                .extract()
                .as(new TypeRef<>() {
                });

        Assertions.assertThat(users)
                .allMatch(u -> u.email().endsWith(emailSuffix), "All has to end with " + emailSuffix)
                .allMatch(u -> u.gender().equalsIgnoreCase(gender), "All to be " + gender);
    }
}
