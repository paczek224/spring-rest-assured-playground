package com.paczek.demoRest.serialization;

import com.paczek.demoRest.BaseTest;
import com.paczek.demoRest.data.Gender;
import com.paczek.demoRest.orders.OrderDto;
import com.paczek.demoRest.orders.UserOrdersSummaryDto;
import com.paczek.demoRest.users.UserDto;
import com.paczek.demoRest.util.Mappers;
import io.restassured.common.mapper.TypeRef;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class DeserializationTests extends BaseTest {

    @Test
    void canDeserializeUserObjects() {

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

    @Test
    void canDeserializeOrderSummaryObjects() {

        List<UserDto> users = given().get("users").then().extract().as(new TypeRef<>() {
        });
        List<OrderDto> allOrders = given().get("orders").then().extract().as(new TypeRef<>() {
        });
        List<UserOrdersSummaryDto> orderSummary = given().get("orders/summary").then().extract().as(new TypeRef<>() {
        });

        List<UserOrdersSummaryDto> expected = allOrders.stream()
                .collect(Collectors.groupingBy(OrderDto::userId))
                .entrySet()
                .stream()
                .map(e -> {
                    final UserDto userInKey = Mappers.find(users, () -> e::getKey);
                    return new UserOrdersSummaryDto(userInKey.firstName(), userInKey.lastName(),
                            BigDecimal.valueOf(e.getValue()
                                            .stream()
                                            .map(OrderDto::price)
                                            .reduce(0.0, Double::sum)).setScale(2, RoundingMode.HALF_UP)
                                    .doubleValue());
                })
                .toList();

        Assertions.assertThat(orderSummary)
                .describedAs("Summary stats has users with their total spending")
                .containsExactly(expected.toArray(UserOrdersSummaryDto[]::new));
    }
}
