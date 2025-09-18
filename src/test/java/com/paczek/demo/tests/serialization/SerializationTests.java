package com.paczek.demo.tests.serialization;

import com.paczek.demo.tests.BaseTest;
import com.paczek.demo.app.users.Gender;
import com.paczek.demo.app.users.UserDto;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

import static io.restassured.RestAssured.given;

@Epic("Test epic")
@Story("Test story")
public class SerializationTests extends BaseTest {

    @Test
    void canSerializeObjects() {

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

    @Test
    void canSerializeObjectsUsingMultipart() throws URISyntaxException {

        final Path of = Path.of(ClassLoader.getSystemResource("static/user.json").toURI());

        List<UserDto> createdUsers = given()
                .multiPart(of.toFile())
                .post("users/import")
                .then()
                .statusCode(201)
                .extract()
                .as(new TypeRef<>() {
                });

        final UserDto createdUser1 = createdUsers.get(0);
        Assertions.assertThat(createdUser1.firstName()).isEqualTo("Maria");
        Assertions.assertThat(createdUser1.lastName()).isEqualTo("Nowak");
        Assertions.assertThat(createdUser1.email()).isEqualTo("mNowak@wp.pl");
        Assertions.assertThat(createdUser1.gender()).isEqualTo(Gender.Female.name());
        Assertions.assertThat(createdUser1.ipAddress()).isEqualTo("0.0.0.1");

        final UserDto createdUser2 = createdUsers.get(1);
        Assertions.assertThat(createdUser2.firstName()).isEqualTo("Jan");
        Assertions.assertThat(createdUser2.lastName()).isEqualTo("Kowalski");
        Assertions.assertThat(createdUser2.email()).isEqualTo("jKowalski@wp.pl");
        Assertions.assertThat(createdUser2.gender()).isEqualTo(Gender.Male.name());
        Assertions.assertThat(createdUser2.ipAddress()).isEqualTo("1.1.1.1");
    }

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
}
