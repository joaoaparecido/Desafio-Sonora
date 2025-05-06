package com.desafio.produtos.controllers;

import com.desafio.produtos.config.ProductControllerTestConfig;
import com.desafio.produtos.domain.City;
import com.desafio.produtos.domain.Product;
import com.desafio.produtos.dto.CreateProductDTO;
import com.desafio.produtos.dto.ProductDTO;
import com.desafio.produtos.dto.ProductMapper;
import com.desafio.produtos.dto.UpdateProductDTO;
import com.desafio.produtos.services.JwtService;
import com.desafio.produtos.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import(ProductControllerTestConfig.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;

    @MockBean
    private JwtService jwtService;

    private Product product1;
    private Product product2;
    private ProductDTO productDTO1;
    private ProductDTO productDTO2;
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

        productDTO1 = new ProductDTO();
        productDTO1.setProductCode(1);
        productDTO1.setProductName("Test Product 1");
        productDTO1.setProductValue(10.99);
        productDTO1.setStock(100);
        productDTO1.setCityId(1);
        productDTO1.setCityName("Test City");

        productDTO2 = new ProductDTO();
        productDTO2.setProductCode(2);
        productDTO2.setProductName("Test Product 2");
        productDTO2.setProductValue(20.99);
        productDTO2.setStock(200);
        productDTO2.setCityId(1);
        productDTO2.setCityName("Test City");

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
    void listAll_shouldReturnAllProducts() throws Exception {
        List<Product> products = Arrays.asList(product1, product2);
        List<ProductDTO> productDTOs = Arrays.asList(productDTO1, productDTO2);

        when(productService.listAll()).thenReturn(products);
        when(productMapper.toDto(product1)).thenReturn(productDTO1);
        when(productMapper.toDto(product2)).thenReturn(productDTO2);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].productCode", is(1)))
                .andExpect(jsonPath("$[0].productName", is("Test Product 1")))
                .andExpect(jsonPath("$[0].productValue", is(10.99)))
                .andExpect(jsonPath("$[0].stock", is(100)))
                .andExpect(jsonPath("$[0].cityId", is(1)))
                .andExpect(jsonPath("$[0].cityName", is("Test City")))
                .andExpect(jsonPath("$[1].productCode", is(2)))
                .andExpect(jsonPath("$[1].productName", is("Test Product 2")))
                .andExpect(jsonPath("$[1].productValue", is(20.99)))
                .andExpect(jsonPath("$[1].stock", is(200)))
                .andExpect(jsonPath("$[1].cityId", is(1)))
                .andExpect(jsonPath("$[1].cityName", is("Test City")));

        verify(productService, times(1)).listAll();
        verify(productMapper, times(1)).toDto(product1);
        verify(productMapper, times(1)).toDto(product2);
    }

    @Test
    void findById_withExistingId_shouldReturnProduct() throws Exception {
        when(productService.findById(1)).thenReturn(Optional.of(product1));
        when(productMapper.toDto(product1)).thenReturn(productDTO1);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productCode", is(1)))
                .andExpect(jsonPath("$.productName", is("Test Product 1")))
                .andExpect(jsonPath("$.productValue", is(10.99)))
                .andExpect(jsonPath("$.stock", is(100)))
                .andExpect(jsonPath("$.cityId", is(1)))
                .andExpect(jsonPath("$.cityName", is("Test City")));

        verify(productService, times(1)).findById(1);
        verify(productMapper, times(1)).toDto(product1);
    }

    @Test
    void findById_withNonExistingId_shouldReturnNotFound() throws Exception {
        when(productService.findById(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).findById(999);
    }

    @Test
    void create_shouldCreateProduct() throws Exception {
        Product newProduct = new Product();
        newProduct.setProductCode(3);
        newProduct.setProductName("New Product");
        newProduct.setProductValue(15.99);
        newProduct.setStock(150);
        newProduct.setCity(city);

        ProductDTO newProductDTO = new ProductDTO();
        newProductDTO.setProductCode(3);
        newProductDTO.setProductName("New Product");
        newProductDTO.setProductValue(15.99);
        newProductDTO.setStock(150);
        newProductDTO.setCityId(1);
        newProductDTO.setCityName("Test City");

        when(productService.save(any(CreateProductDTO.class))).thenReturn(newProduct);
        when(productMapper.toDto(newProduct)).thenReturn(newProductDTO);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createProductDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productCode", is(3)))
                .andExpect(jsonPath("$.productName", is("New Product")))
                .andExpect(jsonPath("$.productValue", is(15.99)))
                .andExpect(jsonPath("$.stock", is(150)))
                .andExpect(jsonPath("$.cityId", is(1)))
                .andExpect(jsonPath("$.cityName", is("Test City")));

        verify(productService, times(1)).save(any(CreateProductDTO.class));
        verify(productMapper, times(1)).toDto(newProduct);
    }

    @Test
    void update_shouldUpdateProduct() throws Exception {
        Product updatedProduct = new Product();
        updatedProduct.setProductCode(1);
        updatedProduct.setProductName("Updated Product");
        updatedProduct.setProductValue(25.99);
        updatedProduct.setStock(250);
        updatedProduct.setCity(city);

        ProductDTO updatedProductDTO = new ProductDTO();
        updatedProductDTO.setProductCode(1);
        updatedProductDTO.setProductName("Updated Product");
        updatedProductDTO.setProductValue(25.99);
        updatedProductDTO.setStock(250);
        updatedProductDTO.setCityId(1);
        updatedProductDTO.setCityName("Test City");

        when(productService.update(eq(1), any(UpdateProductDTO.class))).thenReturn(updatedProduct);
        when(productMapper.toDto(updatedProduct)).thenReturn(updatedProductDTO);

        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProductDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productCode", is(1)))
                .andExpect(jsonPath("$.productName", is("Updated Product")))
                .andExpect(jsonPath("$.productValue", is(25.99)))
                .andExpect(jsonPath("$.stock", is(250)))
                .andExpect(jsonPath("$.cityId", is(1)))
                .andExpect(jsonPath("$.cityName", is("Test City")));

        verify(productService, times(1)).update(eq(1), any(UpdateProductDTO.class));
        verify(productMapper, times(1)).toDto(updatedProduct);
    }

    @Test
    void delete_shouldDeleteProduct() throws Exception {
        doNothing().when(productService).delete(1);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).delete(1);
    }
}
