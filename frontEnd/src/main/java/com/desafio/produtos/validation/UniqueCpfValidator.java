package com.desafio.produtos.validation;

import com.desafio.produtos.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueCpfValidator implements ConstraintValidator<UniqueCpf, String> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        return cpf == null || !userRepository.existsByCpf(cpf);
    }
}
