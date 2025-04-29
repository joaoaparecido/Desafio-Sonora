package com.desafio.produtos.controllers;

import com.desafio.produtos.domain.Product;
import com.desafio.produtos.dto.CreateProductDTO;
import com.desafio.produtos.dto.ProductDTO;
import com.desafio.produtos.dto.ProductMapper;
import com.desafio.produtos.dto.UpdateProductDTO;
import com.desafio.produtos.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public List<ProductDTO> listAll() {
        return productService.listAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Integer id) {
        return productService.findById(id)
                .map(productMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody CreateProductDTO createProductDTO) {
        Product product = productService.save(createProductDTO);
        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Integer id, @RequestBody UpdateProductDTO updateProductDTO) {
        Product updatedProduct = productService.update(id, updateProductDTO);
        return ResponseEntity.ok(productMapper.toDto(updatedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}