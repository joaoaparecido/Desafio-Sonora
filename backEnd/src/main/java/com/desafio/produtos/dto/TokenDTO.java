package com.desafio.produtos.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class TokenDTO {
    private String token;

    public TokenDTO(String Token) {
        this.token = Token;
    }
}


