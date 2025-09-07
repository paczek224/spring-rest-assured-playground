package com.paczek.demoRest.mocking;

import com.fasterxml.jackson.core.type.TypeReference;
import com.paczek.demoRest.users.UsersController;
import com.paczek.demoRest.data.Gender;
import com.paczek.demoRest.users.UserDto;
import com.paczek.demoRest.users.UserEntity;
import com.paczek.demoRest.users.UserService;
import com.paczek.demoRest.util.Mappers;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.paczek.demoRest.data.DataMapper.mapToDto;
import static com.paczek.demoRest.data.DataMapper.writeValueAsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UsersController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import(MockingTests.MockConfig.class)
@AutoConfigureMockMvc
public class MockingTests {

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
    void canMockUserServiceToFindUser() throws Exception {
        long mockedId = 100L;
        UserEntity mockedRepositoryUser = new UserEntity(mockedId, "Lukasz", "Paczek", "test@zzz.com", Gender.Male.name(), "1.1.1.1");
        Mockito.when(userService.getUser(mockedId)).thenReturn(mockedRepositoryUser);

        UserDto result = mapToDto(mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "users/" + mockedId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), new TypeReference<>() {
        });

        Assertions.assertThat(result).isEqualTo(Mappers.map(mockedRepositoryUser));
    }

    @Test
    void canMockUserServiceSaveToCheckIfCreatedUserIsCorrectlyReturned() throws Exception {
        long mockedId = 100L;
        UserEntity postDto = new UserEntity(mockedId, "Lukasz", "Paczek", "test@zzz.com", Gender.Male.name(), "1.1.1.1");
        UserDto mockedCreatedUser = Mappers.map(postDto);
        Mockito.when(userService.save(Mockito.any(UserDto.class))).thenReturn(mockedCreatedUser);

        UserDto result = mapToDto(mockMvc.perform(MockMvcRequestBuilders.post(baseUrl + "users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writeValueAsString(postDto).getBytes()))
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString(), new TypeReference<>() {
        });

        Assertions.assertThat(result).isEqualTo(mockedCreatedUser);
    }
}
