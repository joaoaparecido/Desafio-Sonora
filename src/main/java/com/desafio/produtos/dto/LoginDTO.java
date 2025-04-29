package com.desafio.produtos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginDTO {
    @NotNull
    private String cpf;
    @NotNull
    private String password;
}
