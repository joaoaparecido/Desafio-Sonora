package com.desafio.produtos.services;

import com.desafio.produtos.domain.User;
import com.desafio.produtos.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService {
    private final UserRepository userRepository;

    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean hasSameCpf(String tokenCpf, Integer userId) {
        User user = userRepository.findById(userId)
                .orElse(null);
        if (user == null) {
            return false;
        }
        return user.getCpf().equals(tokenCpf);
    }
}
