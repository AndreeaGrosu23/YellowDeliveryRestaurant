package com.codecool.restaurant.integrationTests;

import com.codecool.restaurant.shoppingCart.ShoppingCart;
import com.codecool.restaurant.shoppingCart.ShoppingCartService;
import com.codecool.restaurant.shoppingCart.payload.AddMealInCartRequest;
import com.codecool.restaurant.shoppingCart.payload.ViewCart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartService shoppingCartServiceMock;

    @MockBean
    private Authentication authenticationMock;

    @Test
    public void whenPostMeal_andNotLogin() throws Exception {
        AddMealInCartRequest meal = new AddMealInCartRequest(
                "1234", "Beef", "http://image.com",
                5.00, 1);

        mockMvc.perform(post("/api/v1/cart")
                .content(String.valueOf(meal))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithMockUser(username="acgrosu", password="1234")
    @Test
    public void testGetAllMealsInCart() throws Exception {

        List<ViewCart> list = new ArrayList<>();

        when(shoppingCartServiceMock.allMealsInCartByAuthenticateUser(authenticationMock)).thenReturn(list);

        mockMvc.perform(get("/api/v1/cart"))
                .andDo(print())
                .andExpect(status().isOk());

    }

}
