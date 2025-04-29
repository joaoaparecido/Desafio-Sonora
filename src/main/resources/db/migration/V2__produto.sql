CREATE TABLE cidade
(
    id   INTEGER PRIMARY KEY,
    nome VARCHAR(150) NOT NULL
);

CREATE TABLE produto
(
    cod_produto   SERIAL PRIMARY KEY,
    nome_produto  VARCHAR(100)   NOT NULL,
    valor_produto DECIMAL(10, 2) NOT NULL,
    estoque       INTEGER        NOT NULL,
    cidade_id     INTEGER REFERENCES cidade (id)
);