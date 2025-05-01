package com.desafio.produtos.services;

import com.desafio.produtos.domain.Product;
import com.desafio.produtos.dto.CreateProductDTO;
import com.desafio.produtos.dto.ProductMapper;
import com.desafio.produtos.dto.UpdateProductDTO;
import com.desafio.produtos.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    public Product save(CreateProductDTO createProductDTO) {
        Product product = productMapper.toEntity(createProductDTO);
        return productRepository.save(product);
    }

    public Product update(Integer id, UpdateProductDTO updateProductDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        productMapper.updateEntityFromDto(existingProduct, updateProductDTO);

        return productRepository.save(existingProduct);
    }

    public void delete(Integer id) {
        productRepository.deleteById(id);
    }
}