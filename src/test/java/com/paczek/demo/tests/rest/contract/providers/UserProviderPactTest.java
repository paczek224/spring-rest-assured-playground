package com.paczek.demo.tests.rest.contract.providers;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import com.paczek.demo.app.users.UserEntity;
import com.paczek.demo.app.users.UserRepository;
import com.paczek.demo.app.users.UserService;
import com.paczek.demo.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Optional;

@Epic("Practice")
@Story("ContractTesting")
@Provider("UserService")
@PactFolder("target/pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserProviderPactTest extends BaseTest {

    @Autowired
    private UserRepository userRepository;

    @LocalServerPort
    int port;

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void verifyPacts(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("User with id=1 exists")
    public void userExists() {
        Optional<UserEntity> byId = userRepository.findById(1L);
        byId.orElseThrow(IllegalStateException::new);
    }
}
