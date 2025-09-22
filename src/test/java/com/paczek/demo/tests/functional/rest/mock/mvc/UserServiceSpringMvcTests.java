package com.paczek.demo.tests.functional.rest.mock.mvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.paczek.demo.app.users.*;
import com.paczek.demo.app.util.Mappers;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static com.paczek.demo.tests.utils.DataMapper.mapToDto;
import static com.paczek.demo.tests.utils.DataMapper.writeValueAsString;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Epic("Practice")
@Story("Mock")
@WebMvcTest(controllers = UsersController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import(UserServiceSpringMvcTests.MockConfig.class)
@AutoConfigureMockMvc
public class UserServiceSpringMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }

    @Value("${baseUrl}")
    protected String baseUrl;

    @Test
    void isAbleToMockUserServiceMethodForGettingUserEntity() throws Exception {
        long mockedId = 100L;
        UserEntity mockedRepositoryUser = new UserEntity(mockedId, "Lukasz", "Paczek", "test@zzz.com", Gender.Male.name(), "1.1.1.1");
        Mockito.when(userService.getUser(mockedId)).thenReturn(mockedRepositoryUser);

        UserDto result = mapToDto(mockMvc.perform(get(baseUrl + "users/" + mockedId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), new TypeReference<>() {
        });

        Assertions.assertThat(result).isEqualTo(Mappers.map(mockedRepositoryUser));
    }

    @Test
    void isAbleToMockUserServiceSaveMethodForSavingNewUser() throws Exception {
        long mockedId = 100L;
        UserDto mockedCreatedUser = new UserDto(mockedId, "Lukasz", "Paczek", "test@zzz.com", Gender.Male.name(), "1.1.1.1");
        Mockito.when(userService.save(Mockito.any(UserDto.class))).thenReturn(mockedCreatedUser);

        UserDto body = new UserDto(0L, "xx", "xx", "xx", "xx", "xx");
        UserDto result = mapToDto(mockMvc.perform(post(baseUrl + "users")
                        .contentType(APPLICATION_JSON)
                        .content(writeValueAsString(body).getBytes()))
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString(), new TypeReference<>() {
        });

        Assertions.assertThat(result).isEqualTo(mockedCreatedUser);
    }
}
