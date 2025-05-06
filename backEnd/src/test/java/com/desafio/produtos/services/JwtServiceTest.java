package com.desafio.produtos.services;

import com.desafio.produtos.domain.Role;
import com.desafio.produtos.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private User user;

    @BeforeEach
    void setUp() {
        // Set up test values for JwtService properties
        ReflectionTestUtils.setField(jwtService, "secretKey", "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 86400000L); // 1 day in milliseconds

        user = new User();
        user.setId(1);
        user.setName("Test User");
        user.setCpf("12345678901");
        user.setPassword("password");
        user.setRole(Role.USER);
    }

    @Test
    void generateToken_shouldGenerateValidToken() {
        // Act
        String token = jwtService.generateToken(user);

        // Assert
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void extractCpf_shouldExtractCpfFromToken() {
        // Arrange
        String token = jwtService.generateToken(user);

        // Act
        String cpf = jwtService.extractCpf(token);

        // Assert
        assertEquals("12345678901", cpf);
    }

    @Test
    void extractRole_shouldExtractRoleFromToken() {
        // Arrange
        String token = jwtService.generateToken(user);

        // Act
        String role = jwtService.getRoleFromToken(token);

        // Assert
        assertEquals("USER", role);
    }

    @Test
    void isTokenValid_withValidToken_shouldReturnTrue() {
        // Arrange
        String token = jwtService.generateToken(user);

        // Act
        boolean isValid = jwtService.isTokenValid(token, user);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void isTokenValid_withInvalidCpf_shouldReturnFalse() {
        // Arrange
        String token = jwtService.generateToken(user);
        
        User differentUser = new User();
        differentUser.setCpf("98765432109");

        // Act
        boolean isValid = jwtService.isTokenValid(token, differentUser);

        // Assert
        assertFalse(isValid);
    }
}