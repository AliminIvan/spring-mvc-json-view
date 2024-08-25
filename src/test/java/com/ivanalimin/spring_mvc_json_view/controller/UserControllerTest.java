package com.ivanalimin.spring_mvc_json_view.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanalimin.spring_mvc_json_view.model.Order;
import com.ivanalimin.spring_mvc_json_view.model.OrderStatus;
import com.ivanalimin.spring_mvc_json_view.model.User;
import com.ivanalimin.spring_mvc_json_view.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = new User(userId, "John Doe", "john.doe@example.com", null);
    }

    @Test
    void testGetAllUsers() throws Exception {
        given(userService.findAll()).willReturn(List.of(user));

        mockMvc.perform(get("/rest/users")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(userId.toString()))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[0].orders").doesNotExist());
    }

    @Test
    void testGetUserById() throws Exception {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setAmount(new BigDecimal("123.45"));
        order.setStatus(OrderStatus.IN_PROGRESS);
        order.setProducts(List.of("Product1", "Product2"));
        order.setUser(user);

        user.setOrders(Set.of(order));

        given(userService.findById(userId)).willReturn(user);

        mockMvc.perform(get("/rest/users/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.orders").isArray())
                .andExpect(jsonPath("$.orders[0].id").value(order.getId().toString()))
                .andExpect(jsonPath("$.orders[0].amount").value(order.getAmount().toString()))
                .andExpect(jsonPath("$.orders[0].status").value(order.getStatus().name()))
                .andExpect(jsonPath("$.orders[0].products[0]").value("Product1"))
                .andExpect(jsonPath("$.orders[0].products[1]").value("Product2"));
    }

    @Test
    void testCreateUser() throws Exception {
        User newUser = new User();
        newUser.setId(UUID.randomUUID()); // Убедитесь, что вы создаете корректный UUID
        newUser.setName("John Doe");
        newUser.setEmail("john.doe@example.com");

        given(userService.save(any(User.class))).willReturn(newUser);

        String jsonUser = objectMapper.writeValueAsString(newUser);

        mockMvc.perform(post("/rest/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonUser)) // Передаем корректный JSON
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(newUser.getId().toString())) // Проверяем, что id присутствует
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void testUpdateUser() throws Exception {
        UUID userId = UUID.randomUUID();
        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName("John Doe Updated");
        updatedUser.setEmail("john.doe.updated@example.com");

        given(userService.update(any(UUID.class), any(User.class))).willReturn(updatedUser);

        String jsonUser = objectMapper.writeValueAsString(updatedUser);

        mockMvc.perform(put("/rest/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.name").value("John Doe Updated"))
                .andExpect(jsonPath("$.email").value("john.doe.updated@example.com"));
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/rest/users/{id}", userId))
                .andExpect(status().isNoContent());
    }
}
