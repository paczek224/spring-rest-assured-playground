package com.paczek.demo.tests.functional.rest.mock.matchers;

import com.paczek.demo.app.users.Gender;
import com.paczek.demo.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("Practice")
@Story("Matchers")
@SpringBootTest
public class MatcherTestsTests extends BaseTest {

    @Test
    void everyItemIsOneOfExpectedValues() {
        given()
                .get("users")
                .then()
                .body("gender", everyItem(oneOf(Stream.of(Gender.values()).map(Enum::name).toArray(String[]::new))));
    }

    @Test
    void everyItemEndsWithAndEqualToIgnoringCase() {
        given()
                .queryParam("email", "pl")
                .queryParam("gender", "male")
                .get("users")
                .then()
                .body("email", everyItem(endsWith("pl")))
                .body("gender", everyItem(equalToIgnoringCase("male")));
    }

    @Test
    void allOfChecksIfTheExaminedObjectMatchesAllMatchers() {
        given()
                .get("lotto")
                .then()
                .body("lotto.winning-numbers",
                        Matchers.allOf(
                                Matchers.everyItem(Matchers.greaterThan(0)),
                                Matchers.everyItem(Matchers.lessThan(50))
                        ));
    }

    @Test
    void anyOfChecksIfTheExaminedObjectMatchesAtLeastOneOfPassedMatchers() {
        given()
                .get("store")
                .then()
                .body("store.book.author[0]",
                        Matchers.anyOf(
                                Matchers.containsString("Nigel"),
                                Matchers.containsString("onPurposeWrong")
                        ));
    }

    @Test
    void containsChecksIfCollectionContainsAllElements() {
        given()
                .get("store")
                .then()
                .body("store.book.title", contains("Sayings of the Century", "Sword of Honour", "Moby Dick", "The Lord of the Rings"));

        given()
                .get("store")
                .then()
                .body("store.book.title", hasItem("Sayings of the Century"));
    }

    @Test
    void containsRelativeOrderChecksCollectionOrderEvenIfHasElementsBetweenPassedElements() {
        given()
                .get("store")
                .then()
                .body("store.book.title", containsInRelativeOrder("Sayings of the Century", "The Lord of the Rings"));
    }

    @Test
    void hasItemUsedWhenCheckingIfCollectionContainsPassedElement() {
        given()
                .get("store")
                .then()
                .body("store.book.title", hasItem("Sayings of the Century"));
    }

    @Test
    void bothCanCombineTwoMatchersAndTwoMustMatch() {
        given()
                .get("store")
                .then()
                .body("store.book.title", Matchers.both(hasItem("Sayings of the Century")).and(hasItem("Moby Dick")));
    }

    @Test
    void eitherCanCombineTwoMatchersAndOnlyOneHasToMatch() {
        given()
                .get("store")
                .then()
                .body("store.book.title", Matchers.either(hasItem("Sayings of the Century")).or(hasItem("onPurposeWrong")));
    }

    @Test
    void closeToChecksIfElementIsCloseToPassedValue() {
        given()
                .get("store")
                .then()
                .body("store.book.price[0]", closeTo(9.00, 0.06));
    }
}
