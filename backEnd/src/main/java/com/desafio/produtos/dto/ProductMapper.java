package com.desafio.produtos.dto;

import com.desafio.produtos.domain.Product;
import com.desafio.produtos.repositories.CityRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final CityRepository cityRepository;

    public ProductMapper(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Product toEntity(CreateProductDTO dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setProductValue(dto.getProductValue());
        product.setStock(dto.getStock());

        if (dto.getCityId() != null) {
            cityRepository.findById(dto.getCityId())
                    .ifPresent(product::setCity);
        }

        return product;
    }

    public Product updateEntityFromDto(Product product, UpdateProductDTO dto) {
        if (dto == null) {
            return product;
        }

        if (dto.getProductName() != null) {
            product.setProductName(dto.getProductName());
        }
        if (dto.getProductValue() != null) {
            product.setProductValue(dto.getProductValue());
        }
        if (dto.getStock() != null) {
            product.setStock(dto.getStock());
        }
        if (dto.getCityId() != null) {
            cityRepository.findById(dto.getCityId())
                    .ifPresent(product::setCity);
        }

        return product;
    }

    public ProductDTO toDto(Product entity) {
        if (entity == null) {
            return null;
        }

        ProductDTO dto = new ProductDTO();
        dto.setProductCode(entity.getProductCode());
        dto.setProductName(entity.getProductName());
        dto.setProductValue(entity.getProductValue());
        dto.setStock(entity.getStock());

        if (entity.getCity() != null) {
            dto.setCityId(entity.getCity().getId());
            dto.setCityName(entity.getCity().getName());
        }

        return dto;
    }
}