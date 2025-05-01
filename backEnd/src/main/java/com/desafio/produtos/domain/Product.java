package com.desafio.produtos.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_produto")
    private Integer productCode;

    @Column(name = "nome_produto")
    private String productName;
    
    @Column(name = "valor_produto")
    private Double productValue;
    
    @Column(name = "estoque")
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "cidade_id")
    private City city;
}