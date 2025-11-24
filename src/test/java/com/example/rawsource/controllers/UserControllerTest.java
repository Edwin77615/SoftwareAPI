package com.example.rawsource.controllers;

import com.example.rawsource.entities.Role;
import com.example.rawsource.entities.dto.user.UserDto;
import com.example.rawsource.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.rawsource.config.JwtAuthenticationFilter;
import com.example.rawsource.config.RateLimitFilter;
import com.example.rawsource.services.JwtService;
import com.example.rawsource.services.TokenRevocationService;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.example.rawsource.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private TokenRevocationService tokenRevocationService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void getAllUsers_noRole_returnsAllUsers() throws Exception {
        UserDto u1 = new UserDto(UUID.randomUUID(), "Alice", "alice@example.com", null, Role.BUYER);
        UserDto u2 = new UserDto(UUID.randomUUID(), "Bob", "bob@example.com", null, Role.PROVIDER);

        when(userService.getAllUsers(null)).thenReturn(List.of(u1, u2));

        mockMvc.perform(get("/api/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void getAllUsers_withRole_returnsFilteredUsers() throws Exception {
        UserDto u1 = new UserDto(UUID.randomUUID(), "BuyerOne", "b1@example.com", null, Role.BUYER);

        when(userService.getAllUsers(Role.BUYER)).thenReturn(List.of(u1));

        mockMvc.perform(get("/api/users")
                        .param("role", "BUYER")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email").value("b1@example.com"));
    }
}
