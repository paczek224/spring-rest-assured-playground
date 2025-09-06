package com.paczek.demoRest.serialization;

import com.paczek.demoRest.BaseTest;
import com.paczek.demoRest.data.Gender;
import com.paczek.demoRest.dto.user.UserDto;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SerializationTests extends BaseTest {

    @Test
    void canSerializeObjects() {

        final UserDto userToCreate = new UserDto("Lukasz", "Paczek", "lpaczek@wp.pl", Gender.Male.name(), "0.0.0.0");
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

        userToCreate.setId(createdUser.getId());
        Assertions.assertThat(createdUser).isEqualTo(userToCreate);
        Assertions.assertThat(fetchedUserById).isEqualTo(userToCreate);
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
        Assertions.assertThat(createdUser1.getFirstName()).isEqualTo("Maria");
        Assertions.assertThat(createdUser1.getLastName()).isEqualTo("Nowak");
        Assertions.assertThat(createdUser1.getEmail()).isEqualTo("mNowak@wp.pl");
        Assertions.assertThat(createdUser1.getGender()).isEqualTo(Gender.Female.name());
        Assertions.assertThat(createdUser1.getIpAddress()).isEqualTo("0.0.0.1");

        final UserDto createdUser2 = createdUsers.get(1);
        Assertions.assertThat(createdUser2.getFirstName()).isEqualTo("Jan");
        Assertions.assertThat(createdUser2.getLastName()).isEqualTo("Kowalski");
        Assertions.assertThat(createdUser2.getEmail()).isEqualTo("jKowalski@wp.pl");
        Assertions.assertThat(createdUser2.getGender()).isEqualTo(Gender.Male.name());
        Assertions.assertThat(createdUser2.getIpAddress()).isEqualTo("1.1.1.1");
    }

    @Test
    void cannotCreateUserWithNullProperties(){
        final UserDto userToCreate = new UserDto();
        given()
                .contentType(ContentType.JSON)
                .body(userToCreate)
                .post("users")
                .then()
                .statusCode(400);
    }
}
