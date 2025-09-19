package com.paczek.demo.tests.rest.mock.wiremock.products;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.paczek.demo.app.products.CurrencyRateResponse;
import com.paczek.demo.app.products.ProductDto;
import com.paczek.demo.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.restassured.common.mapper.TypeRef;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Set;

import static com.paczek.demo.app.util.Mappers.calculatePlnToCurrency;
import static io.restassured.RestAssured.given;

@Epic("Practice")
@Epic("App - Products")
@Story("WireMock")
@Story("Currency")
@WireMockTest(httpPort = 8081)
public class ProductsCurrencyTests extends BaseTest {

    private static Set<ProductDto> products;

    @BeforeAll
    public static void setupAllProducts() {
        products = given()
                .get("products")
                .then()
                .extract()
                .as(new TypeRef<>() {
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {"EUR", "USD", "DKK"})
    void productsCurrenciesAreCorrectlyCalculated(String currency) {
        ProductDto productDto = new ArrayList<>(products).get(0);
        ProductDto productByIdInCurrency = given()
                .pathParams("id", productDto.id())
                .queryParam("currency", currency)
                .get("products/{id}")
                .as(ProductDto.class);

        CurrencyRateResponse currencyDto = given()
                .queryParam("symbols", currency)
                .get("currency")
                .as(CurrencyRateResponse.class);

        BigDecimal expectedPrice = calculatePlnToCurrency(productDto.price(), currencyDto.rates().get(0).mid());
        Assertions.assertThat(productByIdInCurrency.price()).isEqualTo(expectedPrice.doubleValue());
    }
}
