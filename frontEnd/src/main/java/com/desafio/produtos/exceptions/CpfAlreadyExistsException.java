package com.desafio.produtos.exceptions;

public class CpfAlreadyExistsException extends RuntimeException {
    public CpfAlreadyExistsException(String message) {
        super(message);
    }
}