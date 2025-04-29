package com.desafio.produtos.services;

import com.desafio.produtos.domain.User;
import com.desafio.produtos.dto.LoginDTO;
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

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> listAll() {
        return userRepository.findAll();
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
}