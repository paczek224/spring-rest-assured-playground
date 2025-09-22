package com.paczek.demo.tests.functional.rest.orders;

import com.paczek.demo.app.orders.OrderDto;
import com.paczek.demo.app.orders.UserOrdersSummaryDto;
import com.paczek.demo.app.users.UserDto;
import com.paczek.demo.app.util.Mappers;
import com.paczek.demo.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.common.mapper.TypeRef;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

@Epic("App - Orders")
@Story("OrderSummary")
public class OrderSummaryTests extends BaseTest {

    @Test
    void orderSummaryReturnConsistentDataForUsers() {

        List<UserDto> users = given().get("users").then().extract().as(new TypeRef<>() {
        });
        List<OrderDto> allOrders = given().get("orders").then().extract().as(new TypeRef<>() {
        });
        List<UserOrdersSummaryDto> orderSummary = given().get("orders/summary").then().extract().as(new TypeRef<>() {
        });

        List<UserOrdersSummaryDto> expected = allOrders.stream()
                .collect(Collectors.groupingBy(
                        OrderDto::userId,
                        Collectors.summingDouble(OrderDto::price)
                ))
                .entrySet()
                .stream()
                .map(e -> {
                    final UserDto userInKey = Mappers.find(users, () -> e::getKey);
                    return new UserOrdersSummaryDto(
                            userInKey.firstName(),
                            userInKey.lastName(),
                            BigDecimal.valueOf(e.getValue()).setScale(2, RoundingMode.HALF_UP).doubleValue());
                })
                .toList();

        Assertions.assertThat(orderSummary)
                .describedAs("Summary stats has users with their total spending")
                .containsExactly(expected.toArray(UserOrdersSummaryDto[]::new));
    }
}
