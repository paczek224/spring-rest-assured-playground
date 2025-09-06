package com.paczek.demoRest.serialization;

import com.paczek.demoRest.BaseTest;
import com.paczek.demoRest.data.Gender;
import com.paczek.demoRest.dto.user.UserDto;
import io.restassured.common.mapper.TypeRef;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class DeserializationTests extends BaseTest {

    @Test
    void canDeserializeObjects() {

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
                .allMatch(u -> u.getEmail().endsWith(emailSuffix), "All has to end with " + emailSuffix)
                .allMatch(u -> u.getGender().equalsIgnoreCase(gender), "All to be " + gender);
    }
}
