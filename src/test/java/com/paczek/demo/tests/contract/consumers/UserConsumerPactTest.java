package com.paczek.demo.tests.contract.consumers;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.MockServerConfig;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.paczek.demo.app.users.Gender;
import com.paczek.demo.app.users.UserDto;
import com.paczek.demo.app.users.UserRepository;
import com.paczek.demo.app.users.UserService;
import com.paczek.demo.app.util.Mappers;
import com.paczek.demo.tests.BaseTest;
import com.paczek.demo.tests.utils.DataMapper;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Practice")
@Story("ContractTesting")
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "UserService")
@MockServerConfig(port = "8081")
public class UserConsumerPactTest extends BaseTest {

    private UserDto test;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup(){
        if(Objects.isNull(test)){
            test = Mappers.map(userRepository.findById(1L).orElseThrow(IllegalStateException::new));
        }
    }

    @SneakyThrows
    @Pact(consumer = "UserClient")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
                .given("User with id=1 exists")
                .uponReceiving("A request for user with id=1")
                .path("/users/" + test.id())
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(DataMapper.writeValueAsString(test))
                .toPact();
    }

    @SneakyThrows
    @Test
    void testUserApi(MockServer mockServer) {
        URL url = new URI(mockServer.getUrl() + "/users/" + test.id()).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            String response = scanner.useDelimiter("\\A").next();
            assertThat(response).isEqualTo(DataMapper.writeValueAsString(test));
        }
    }
}
