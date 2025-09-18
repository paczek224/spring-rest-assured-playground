package com.paczek.demo.tests.users;

import com.paczek.demo.app.users.Gender;
import com.paczek.demo.app.users.UserDto;
import com.paczek.demo.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.common.mapper.TypeRef;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

import static io.restassured.RestAssured.given;

@Epic("App - users")
@Story("Import users")
public class ImportUsersTests extends BaseTest {

    @Test
    void canImportUserFromFile() throws URISyntaxException {

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
}
