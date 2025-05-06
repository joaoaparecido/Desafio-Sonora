package com.desafio.produtos.controllers;

import com.desafio.produtos.config.TestSecurityConfig;
import com.desafio.produtos.domain.Role;
import com.desafio.produtos.domain.User;
import com.desafio.produtos.dto.*;
import com.desafio.produtos.services.JwtService;
import com.desafio.produtos.services.LoginService;
import com.desafio.produtos.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private LoginService loginService;

    @MockBean
    private JwtService jwtService;

    private User user1;
    private User user2;
    private UserDTO userDTO1;
    private UserDTO userDTO2;
    private CreateUserDTO createUserDTO;
    private UpdateUserDTO updateUserDTO;
    private LoginDTO loginDTO;
    private TokenDTO tokenDTO;

    @BeforeEach
    void setUp() {
        // Setup test data
        user1 = new User();
        user1.setId(1);
        user1.setName("Test User 1");
        user1.setCpf("12345678901");
        user1.setPassword("password1");
        user1.setRole(Role.ADMIN);

        user2 = new User();
        user2.setId(2);
        user2.setName("Test User 2");
        user2.setCpf("98765432109");
        user2.setPassword("password2");
        user2.setRole(Role.USER);

        userDTO1 = new UserDTO();
        userDTO1.setId(1);
        userDTO1.setName("Test User 1");
        userDTO1.setCpf("12345678901");
        userDTO1.setRole("ADMIN");

        userDTO2 = new UserDTO();
        userDTO2.setId(2);
        userDTO2.setName("Test User 2");
        userDTO2.setCpf("98765432109");
        userDTO2.setRole("USER");

        createUserDTO = new CreateUserDTO();
        createUserDTO.setName("New User");
        createUserDTO.setCpf("11122233344");
        createUserDTO.setPassword("newpassword");
        createUserDTO.setRole("USER");

        updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setName("Updated User");
        updateUserDTO.setPassword("updatedpassword");

        loginDTO = new LoginDTO("12345678901", "password1", "ADMIN");
        tokenDTO = new TokenDTO("test-jwt-token");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void listAll_shouldReturnAllUsers() throws Exception {
        List<User> users = Arrays.asList(user1, user2);
        List<UserDTO> userDTOs = Arrays.asList(userDTO1, userDTO2);

        when(userService.listAll()).thenReturn(users);
        when(userMapper.toDto(user1)).thenReturn(userDTO1);
        when(userMapper.toDto(user2)).thenReturn(userDTO2);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Test User 1")))
                .andExpect(jsonPath("$[0].cpf", is("12345678901")))
                .andExpect(jsonPath("$[0].role", is("ADMIN")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Test User 2")))
                .andExpect(jsonPath("$[1].cpf", is("98765432109")))
                .andExpect(jsonPath("$[1].role", is("USER")));

        verify(userService, times(1)).listAll();
        verify(userMapper, times(1)).toDto(user1);
        verify(userMapper, times(1)).toDto(user2);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findById_withExistingId_shouldReturnUser() throws Exception {
        when(userService.findById(1)).thenReturn(Optional.of(user1));
        when(userMapper.toDto(user1)).thenReturn(userDTO1);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test User 1")))
                .andExpect(jsonPath("$.cpf", is("12345678901")))
                .andExpect(jsonPath("$.role", is("ADMIN")));

        verify(userService, times(1)).findById(1);
        verify(userMapper, times(1)).toDto(user1);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findById_withNonExistingId_shouldReturnNotFound() throws Exception {
        when(userService.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).findById(999);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create_shouldCreateUser() throws Exception {
        User newUser = new User();
        newUser.setId(3);
        newUser.setName("New User");
        newUser.setCpf("11122233344");
        newUser.setPassword("newpassword");
        newUser.setRole(Role.USER);

        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setId(3);
        newUserDTO.setName("New User");
        newUserDTO.setCpf("11122233344");
        newUserDTO.setRole("USER");

        when(userMapper.toEntity(any(CreateUserDTO.class))).thenReturn(newUser);
        when(userService.save(any(User.class))).thenReturn(newUser);
        when(userMapper.toDto(newUser)).thenReturn(newUserDTO);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("New User")))
                .andExpect(jsonPath("$.cpf", is("11122233344")))
                .andExpect(jsonPath("$.role", is("USER")));

        verify(userMapper, times(1)).toEntity(any(CreateUserDTO.class));
        verify(userService, times(1)).save(any(User.class));
        verify(userMapper, times(1)).toDto(newUser);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete_shouldDeleteUser() throws Exception {
        doNothing().when(userService).delete(1);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).delete(1);
    }

    @Test
    void login_withValidCredentials_shouldReturnToken() throws Exception {
        when(loginService.login(eq("12345678901"), eq("password1"), eq(Role.ADMIN))).thenReturn(tokenDTO);

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is("test-jwt-token")));

        verify(loginService, times(1)).login(eq("12345678901"), eq("password1"), eq(Role.ADMIN));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update_shouldUpdateUser() throws Exception {
        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setName("Updated User");
        updatedUser.setCpf("12345678901");
        updatedUser.setPassword("updatedpassword");
        updatedUser.setRole(Role.ADMIN);

        UserDTO updatedUserDTO = new UserDTO();
        updatedUserDTO.setId(1);
        updatedUserDTO.setName("Updated User");
        updatedUserDTO.setCpf("12345678901");
        updatedUserDTO.setRole("ADMIN");

        when(userService.update(eq(1), any(UpdateUserDTO.class))).thenReturn(updatedUser);
        when(userMapper.toDto(updatedUser)).thenReturn(updatedUserDTO);

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Updated User")))
                .andExpect(jsonPath("$.cpf", is("12345678901")))
                .andExpect(jsonPath("$.role", is("ADMIN")));

        verify(userService, times(1)).update(eq(1), any(UpdateUserDTO.class));
        verify(userMapper, times(1)).toDto(updatedUser);
    }
}
