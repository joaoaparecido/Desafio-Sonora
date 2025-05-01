package com.desafio.produtos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Integer productCode;
    private String productName;
    private Double productValue;
    private Integer stock;
    private Integer cityId;
    private String cityName;
}