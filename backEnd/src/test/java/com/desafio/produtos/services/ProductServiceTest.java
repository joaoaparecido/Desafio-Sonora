package com.desafio.produtos.services;

import com.desafio.produtos.domain.City;
import com.desafio.produtos.domain.Product;
import com.desafio.produtos.dto.CreateProductDTO;
import com.desafio.produtos.dto.ProductMapper;
import com.desafio.produtos.dto.UpdateProductDTO;
import com.desafio.produtos.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product product1;
    private Product product2;
    private CreateProductDTO createProductDTO;
    private UpdateProductDTO updateProductDTO;
    private City city;

    @BeforeEach
    void setUp() {
        // Setup city
        city = new City();
        city.setId(1);
        city.setName("Test City");

        // Setup test data
        product1 = new Product();
        product1.setProductCode(1);
        product1.setProductName("Test Product 1");
        product1.setProductValue(10.99);
        product1.setStock(100);
        product1.setCity(city);

        product2 = new Product();
        product2.setProductCode(2);
        product2.setProductName("Test Product 2");
        product2.setProductValue(20.99);
        product2.setStock(200);
        product2.setCity(city);

        createProductDTO = new CreateProductDTO();
        createProductDTO.setProductName("New Product");
        createProductDTO.setProductValue(15.99);
        createProductDTO.setStock(150);
        createProductDTO.setCityId(1);

        updateProductDTO = new UpdateProductDTO();
        updateProductDTO.setProductName("Updated Product");
        updateProductDTO.setProductValue(25.99);
        updateProductDTO.setStock(250);
        updateProductDTO.setCityId(1);
    }

    @Test
    void listAll_shouldReturnAllProducts() {
        // Arrange
        List<Product> expectedProducts = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(expectedProducts);

        // Act
        List<Product> actualProducts = productService.listAll();

        // Assert
        assertEquals(expectedProducts, actualProducts);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void findById_withExistingId_shouldReturnProduct() {
        // Arrange
        when(productRepository.findById(1)).thenReturn(Optional.of(product1));

        // Act
        Optional<Product> result = productService.findById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(product1, result.get());
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    void findById_withNonExistingId_shouldReturnEmpty() {
        // Arrange
        when(productRepository.findById(999)).thenReturn(Optional.empty());

        // Act
        Optional<Product> result = productService.findById(999);

        // Assert
        assertFalse(result.isPresent());
        verify(productRepository, times(1)).findById(999);
    }

    @Test
    void save_shouldSaveProduct() {
        // Arrange
        Product newProduct = new Product();
        newProduct.setProductCode(3);
        newProduct.setProductName("New Product");
        newProduct.setProductValue(15.99);
        newProduct.setStock(150);
        newProduct.setCity(city);

        when(productMapper.toEntity(createProductDTO)).thenReturn(newProduct);
        when(productRepository.save(newProduct)).thenReturn(newProduct);

        // Act
        Product savedProduct = productService.save(createProductDTO);

        // Assert
        assertEquals(newProduct, savedProduct);
        verify(productMapper, times(1)).toEntity(createProductDTO);
        verify(productRepository, times(1)).save(newProduct);
    }

    @Test
    void update_withExistingProduct_shouldUpdateProduct() {
        // Arrange
        when(productRepository.findById(1)).thenReturn(Optional.of(product1));
        when(productMapper.updateEntityFromDto(product1, updateProductDTO)).thenReturn(product1);
        when(productRepository.save(product1)).thenReturn(product1);

        // Act
        Product updatedProduct = productService.update(1, updateProductDTO);

        // Assert
        assertEquals(product1, updatedProduct);
        verify(productRepository, times(1)).findById(1);
        verify(productMapper, times(1)).updateEntityFromDto(product1, updateProductDTO);
        verify(productRepository, times(1)).save(product1);
    }

    @Test
    void update_withNonExistingProduct_shouldThrowException() {
        // Arrange
        when(productRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.update(999, updateProductDTO));
        verify(productRepository, times(1)).findById(999);
        verify(productMapper, never()).updateEntityFromDto(any(), any());
        verify(productRepository, never()).save(any());
    }

    @Test
    void delete_shouldDeleteProduct() {
        // Arrange
        doNothing().when(productRepository).deleteById(1);

        // Act
        productService.delete(1);

        // Assert
        verify(productRepository, times(1)).deleteById(1);
    }
}
