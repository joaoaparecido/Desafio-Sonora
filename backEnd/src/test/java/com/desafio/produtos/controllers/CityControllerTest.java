package com.desafio.produtos.controllers;

import com.desafio.produtos.config.TestSecurityConfig;
import com.desafio.produtos.domain.City;
import com.desafio.produtos.dto.CityDTO;
import com.desafio.produtos.dto.CityMapper;
import com.desafio.produtos.services.CityService;
import com.desafio.produtos.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CityController.class)
@Import(TestSecurityConfig.class)
class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CityService cityService;

    @MockBean
    private CityMapper cityMapper;

    @MockBean
    private JwtService jwtService;

    private City city1;
    private City city2;
    private CityDTO cityDTO1;
    private CityDTO cityDTO2;

    @BeforeEach
    void setUp() {
        // Setup test data
        city1 = new City();
        city1.setId(1);
        city1.setName("Test City 1");

        city2 = new City();
        city2.setId(2);
        city2.setName("Test City 2");

        cityDTO1 = new CityDTO();
        cityDTO1.setId(1);
        cityDTO1.setName("Test City 1");

        cityDTO2 = new CityDTO();
        cityDTO2.setId(2);
        cityDTO2.setName("Test City 2");
    }

    @Test
    void listAll_shouldReturnAllCities() throws Exception {
        List<City> cities = Arrays.asList(city1, city2);
        List<CityDTO> cityDTOs = Arrays.asList(cityDTO1, cityDTO2);

        when(cityService.listAll()).thenReturn(cities);
        when(cityMapper.toDto(city1)).thenReturn(cityDTO1);
        when(cityMapper.toDto(city2)).thenReturn(cityDTO2);

        mockMvc.perform(get("/api/cidades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Test City 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Test City 2")));

        verify(cityService, times(1)).listAll();
        verify(cityMapper, times(1)).toDto(city1);
        verify(cityMapper, times(1)).toDto(city2);
    }
}
