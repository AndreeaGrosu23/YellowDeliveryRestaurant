package com.codecool.restaurant;

import com.codecool.restaurant.user.User;
import com.codecool.restaurant.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//Integration test
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {
    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService mockUserService;

    @MockBean
    private User mockUser;

    @Test
    public void saveEmptyUser() throws Exception {
        String newUser = "{\"firstName\":\"\", \"lastName\":\"\", \"userName\":\"\" , \"emailAddress\":\"\", \"deliveryAddress\":\"\", \"phoneNumber\":\"\", \"password\":\"\"}";

        mockMvc.perform(post("/api/v1/user")
                .content(newUser)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(6)))
                .andExpect(jsonPath("$.errors", hasItem("First name is mandatory")))
                .andExpect(jsonPath("$.errors", hasItem("Last name is mandatory")))
                .andExpect(jsonPath("$.errors", hasItem("Username is mandatory")))
                .andExpect(jsonPath("$.errors", hasItem("E-mail is mandatory")))
                .andExpect(jsonPath("$.errors", hasItem("Password is mandatory")))
                .andExpect(jsonPath("$.errors", hasItem("Password must be minimum 4 characters")));

        verify(mockUserService, times(0)).addUser(mockUser);
    }
}
