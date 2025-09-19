package com.paczek.demo.tests.testcontainers.products;

import com.paczek.demo.app.products.ProductDto;
import com.paczek.demo.tests.testcontainers.TestContainerTest;
import com.paczek.demo.tests.utils.DataMapper;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Epic("Practice")
@Epic("App - Products")
@Story("TestContainers")
@Story("Currency")
public class ProductsTestContainerTest extends TestContainerTest {

    public static Stream<Arguments> products() {
        Supplier<String> random = ()-> RandomStringUtils.secure().nextAlphabetic(25);
        return Stream.of(
                Arguments.arguments(new ProductDto(null, random.get(), 22.22, random.get(), LocalDateTime.now(), "PLN")),
                Arguments.arguments(new ProductDto(null, random.get(), 22.22, random.get(), LocalDateTime.now(), "PLN"))
        );
    }

    @ParameterizedTest
    @MethodSource(value = "products")
    void isAbleToSaveAndGetProducts(ProductDto productBody) {

        final Predicate<ProductDto> byName = p -> productBody.name().equals(p.name());

        controller.addProduct(productBody);

        final List<ProductDto> allProducts = controller.products(null, null).getBody();
        final ProductDto byNameInAllProductEndpoint = DataMapper.getElement(allProducts, byName);
        final ProductDto byIdInDetailsEndpoint = controller.getProduct(byNameInAllProductEndpoint.id(), null).getBody();

        Assertions.assertThat(byIdInDetailsEndpoint.name()).isEqualTo(productBody.name());
        Assertions.assertThat(byIdInDetailsEndpoint.price()).isEqualTo(productBody.price());
        Assertions.assertThat(byIdInDetailsEndpoint.description()).isEqualTo(productBody.description());
        Assertions.assertThat(byIdInDetailsEndpoint.createdDate()).isEqualToIgnoringNanos(productBody.createdDate());
        Assertions.assertThat(byIdInDetailsEndpoint.currency()).isEqualTo(productBody.currency());

        Assertions.assertThat(byIdInDetailsEndpoint).isEqualTo(byNameInAllProductEndpoint);
    }
}
