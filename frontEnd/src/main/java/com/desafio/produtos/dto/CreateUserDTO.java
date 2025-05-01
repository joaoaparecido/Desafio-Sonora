package com.desafio.produtos.dto;

import com.desafio.produtos.validation.UniqueCpf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    private String name;
    private String password;
    @UniqueCpf
    private String cpf;
    private String role;
}