package com.desafio.produtos.services;

import com.desafio.produtos.domain.Role;
import com.desafio.produtos.domain.User;
import com.desafio.produtos.dto.UpdateUserDTO;
import com.desafio.produtos.dto.UserMapper;
import com.desafio.produtos.exceptions.CpfAlreadyExistsException;
import com.desafio.produtos.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;
    private UpdateUserDTO updateUserDTO;

    @BeforeEach
    void setUp() {
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

        updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setName("Updated User");
        updateUserDTO.setPassword("updatedpassword");
    }

    @Test
    void listAll_shouldReturnAllUsers() {
        // Arrange
        List<User> expectedUsers = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userService.listAll();

        // Assert
        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findById_withExistingId_shouldReturnUser() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(user1));

        // Act
        Optional<User> result = userService.findById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user1, result.get());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void findById_withNonExistingId_shouldReturnEmpty() {
        // Arrange
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.findById(999);

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(999);
    }

    @Test
    void save_withNewUser_shouldSaveUser() {
        // Arrange
        User newUser = new User();
        newUser.setCpf("11122233344");
        newUser.setPassword("newpassword");

        when(userRepository.existsByCpf("11122233344")).thenReturn(false);
        when(passwordEncoder.encode("newpassword")).thenReturn("encodedpassword");
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        // Act
        User savedUser = userService.save(newUser);

        // Assert
        assertEquals(newUser, savedUser);
        assertEquals("encodedpassword", newUser.getPassword());
        verify(userRepository, times(1)).existsByCpf("11122233344");
        verify(passwordEncoder, times(1)).encode("newpassword");
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void save_withExistingCpf_shouldThrowException() {
        // Arrange
        User newUser = new User();
        newUser.setCpf("12345678901");
        newUser.setPassword("newpassword");

        when(userRepository.existsByCpf("12345678901")).thenReturn(true);

        // Act & Assert
        assertThrows(CpfAlreadyExistsException.class, () -> userService.save(newUser));
        verify(userRepository, times(1)).existsByCpf("12345678901");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void delete_shouldDeleteUser() {
        // Arrange
        doNothing().when(userRepository).deleteById(1);

        // Act
        userService.delete(1);

        // Assert
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void update_withExistingUser_shouldUpdateUser() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        when(userMapper.updateEntityFromDto(user1, updateUserDTO)).thenReturn(user1);
        when(passwordEncoder.encode("updatedpassword")).thenReturn("encodedupdatedpassword");
        when(userRepository.save(user1)).thenReturn(user1);

        // Act
        User updatedUser = userService.update(1, updateUserDTO);

        // Assert
        assertEquals(user1, updatedUser);
        assertEquals("encodedupdatedpassword", user1.getPassword());
        verify(userRepository, times(1)).findById(1);
        verify(userMapper, times(1)).updateEntityFromDto(user1, updateUserDTO);
        verify(passwordEncoder, times(1)).encode("updatedpassword");
        verify(userRepository, times(1)).save(user1);
    }

    @Test
    void update_withNonExistingUser_shouldThrowException() {
        // Arrange
        when(userRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.update(999, updateUserDTO));
        verify(userRepository, times(1)).findById(999);
        verify(userMapper, never()).updateEntityFromDto(any(), any());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any());
    }
}
