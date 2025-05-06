package com.desafio.produtos.services;

import com.desafio.produtos.domain.User;
import com.desafio.produtos.dto.LoginDTO;
import com.desafio.produtos.dto.UpdateUserDTO;
import com.desafio.produtos.dto.UserMapper;
import com.desafio.produtos.exceptions.CpfAlreadyExistsException;
import com.desafio.produtos.exceptions.InvalidCredentialsException;
import com.desafio.produtos.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public List<User> listAll(Integer userId) {
        return this.findById(userId)
                .map(List::of)
                .orElse(List.of());
    }


    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        if (user.getId() == null && userRepository.existsByCpf(user.getCpf())) {
            throw new CpfAlreadyExistsException("CPF já cadastrado: " + user.getCpf());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public User update(Integer id, UpdateUserDTO updateUserDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        userMapper.updateEntityFromDto(existingUser, updateUserDTO);

        if (updateUserDTO.getPassword() != null && !updateUserDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
        }

        return userRepository.save(existingUser);
    }
}
