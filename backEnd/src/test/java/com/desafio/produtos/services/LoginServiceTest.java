package com.desafio.produtos.services;

import com.desafio.produtos.domain.Role;
import com.desafio.produtos.domain.User;
import com.desafio.produtos.dto.TokenDTO;
import com.desafio.produtos.exceptions.InvalidCredentialsException;
import com.desafio.produtos.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginService loginService;

    private User user;
    private String token;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setName("Test User");
        user.setCpf("12345678901");
        user.setPassword("encodedPassword");
        user.setRole(Role.USER);

        token = "test-jwt-token";
    }

    @Test
    void login_withValidCredentials_shouldReturnToken() {
        // Arrange
        String cpf = "12345678901";
        String password = "password";

        when(userRepository.getUserByCpf(cpf)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn(token);

        // Act
        TokenDTO result = loginService.login(cpf, password, Role.USER);

        // Assert
        assertNotNull(result);
        assertEquals(token, result.getToken());
        verify(userRepository, times(1)).getUserByCpf(cpf);
        verify(passwordEncoder, times(1)).matches(password, user.getPassword());
        verify(jwtService, times(1)).generateToken(user);
    }

    @Test
    void login_withInvalidCpf_shouldThrowException() {
        // Arrange
        String cpf = "nonexistentcpf";
        String password = "password";

        when(userRepository.getUserByCpf(cpf)).thenReturn(null);

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> loginService.login(cpf, password, Role.USER));
        verify(userRepository, times(1)).getUserByCpf(cpf);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void login_withInvalidPassword_shouldThrowException() {
        // Arrange
        String cpf = "12345678901";
        String password = "wrongpassword";

        when(userRepository.getUserByCpf(cpf)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> loginService.login(cpf, password, Role.USER));
        verify(userRepository, times(1)).getUserByCpf(cpf);
        verify(passwordEncoder, times(1)).matches(password, user.getPassword());
        verify(jwtService, never()).generateToken(any());
    }
}
