package com.paczek.demo.tests.functional.kafka;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@Epic("Practice")
@Story("Kafka")
public class KafkaDemoTopicTests extends KafkaTest {

    @Test
    void userCanSendMessageDirectlyToTheTopic() {
        final String queueTestMessage = "Hello from Listener " + System.currentTimeMillis();
        kafkaTemplate.send(DEMO_TOPIC, queueTestMessage);
        Assertions.assertThat(getKafkaMessages()).contains(queueTestMessage);
    }

    @Test
    void userCanSendMessageToTheTopicViaEndpoint() {
        final String queueTestMessage = "Hello from Listener " + System.currentTimeMillis();

        given()
                .queryParam("msg", queueTestMessage)
                .get("send")
                .then()
                .statusCode(200);

        Assertions.assertThat(getKafkaMessages()).contains(queueTestMessage);
    }
}
