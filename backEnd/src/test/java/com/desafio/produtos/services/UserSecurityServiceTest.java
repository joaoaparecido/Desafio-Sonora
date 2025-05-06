package com.desafio.produtos.services;

import com.desafio.produtos.domain.Role;
import com.desafio.produtos.domain.User;
import com.desafio.produtos.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSecurityServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserSecurityService userSecurityService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setName("Test User");
        user.setCpf("12345678901");
        user.setPassword("password");
        user.setRole(Role.USER);
    }

    @Test
    void hasSameCpf_withMatchingCpf_shouldReturnTrue() {
        // Arrange
        String tokenCpf = "12345678901";
        Integer userId = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        boolean result = userSecurityService.hasSameCpf(tokenCpf, userId);

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void hasSameCpf_withNonMatchingCpf_shouldReturnFalse() {
        // Arrange
        String tokenCpf = "98765432109";
        Integer userId = 1;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        boolean result = userSecurityService.hasSameCpf(tokenCpf, userId);

        // Assert
        assertFalse(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void hasSameCpf_withNonExistingUser_shouldReturnFalse() {
        // Arrange
        String tokenCpf = "12345678901";
        Integer userId = 999;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        boolean result = userSecurityService.hasSameCpf(tokenCpf, userId);

        // Assert
        assertFalse(result);
        verify(userRepository, times(1)).findById(userId);
    }
}