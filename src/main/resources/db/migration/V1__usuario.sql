CREATE TABLE usuario
(
    id    SERIAL PRIMARY KEY,
    nome  VARCHAR(100)       NOT NULL,
    senha VARCHAR(255)       NOT NULL,
    cpf   VARCHAR(14) UNIQUE NOT NULL,
    role  VARCHAR(20)        NOT NULL
);