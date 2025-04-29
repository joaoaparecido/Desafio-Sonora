package com.desafio.produtos.services;

import com.desafio.produtos.domain.User;
import com.desafio.produtos.dto.LoginDTO;
import com.desafio.produtos.exceptions.InvalidCredentialsException;
import com.desafio.produtos.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String cpf, String password) {
        User userFromDatabase = userRepository.getUserByCpf(cpf);

        if (userFromDatabase == null || !passwordEncoder.matches(password, userFromDatabase.getPassword())) {
            throw new InvalidCredentialsException("Crendenciais incorretas");
        }

        return jwtService.generateToken(userFromDatabase);
    }
}
