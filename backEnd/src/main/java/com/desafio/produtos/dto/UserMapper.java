package com.desafio.produtos.dto;

import com.desafio.produtos.domain.Role;
import com.desafio.produtos.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User updateEntityFromDto(User user, UpdateUserDTO dto) {
        if (dto == null) {
            return user;
        }

        if (dto.getName() != null) {
            user.setName(dto.getName());
        }
        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }
        // Password is handled separately in the service

        return user;
    }

    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setCpf(dto.getCpf());
        user.setRole(dto.getRole());

        return user;
    }

    public User toEntity(CreateUserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        user.setCpf(dto.getCpf());
        user.setRole(dto.getRole());

        return user;
    }

    public UserDTO toDto(User entity) {
        if (entity == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCpf(entity.getCpf());
        dto.setRole(entity.getRole().toString());

        return dto;
    }
}
