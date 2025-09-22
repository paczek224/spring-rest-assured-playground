package com.paczek.demo.tests.functional.rest.users;

import com.paczek.demo.app.users.Gender;
import com.paczek.demo.app.users.UserDto;
import com.paczek.demo.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;


@Epic("App - users")
@Story("Create user")
public class CreatingUsersTests extends BaseTest {

    @Test
    void canCreateUser() {

        final UserDto userToCreate = new UserDto(null, "Lukasz", "Paczek", "lpaczek@wp.pl", Gender.Male.name(), "0.0.0.0");
        final UserDto createdUser = given()
                .contentType(ContentType.JSON)
                .body(userToCreate)
                .post("users")
                .then()
                .statusCode(201)
                .extract()
                .as(UserDto.class);

        final UserDto fetchedUserById = given()
                .pathParams("id", createdUser.getId())
                .get("users/{id}")
                .then()
                .extract()
                .as(UserDto.class);

        Assertions.assertThat(createdUser.firstName()).isEqualTo(userToCreate.firstName());
        Assertions.assertThat(createdUser.lastName()).isEqualTo(userToCreate.lastName());
        Assertions.assertThat(createdUser.email()).isEqualTo(userToCreate.email());
        Assertions.assertThat(createdUser.gender()).isEqualTo(userToCreate.gender());
        Assertions.assertThat(createdUser.ipAddress()).isEqualTo(userToCreate.ipAddress());

        Assertions.assertThat(fetchedUserById.firstName()).isEqualTo(userToCreate.firstName());
        Assertions.assertThat(fetchedUserById.lastName()).isEqualTo(userToCreate.lastName());
        Assertions.assertThat(fetchedUserById.email()).isEqualTo(userToCreate.email());
        Assertions.assertThat(fetchedUserById.gender()).isEqualTo(userToCreate.gender());
        Assertions.assertThat(fetchedUserById.ipAddress()).isEqualTo(userToCreate.ipAddress());
    }
}
