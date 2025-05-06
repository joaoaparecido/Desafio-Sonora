package com.desafio.produtos.repositories;

import com.desafio.produtos.domain.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByCpf(String cpf);

    User getUserByCpf(@NotNull String cpf);
}