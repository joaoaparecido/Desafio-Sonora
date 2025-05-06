package com.desafio.produtos.services;

import com.desafio.produtos.domain.City;
import com.desafio.produtos.repositories.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    private City city1;
    private City city2;

    @BeforeEach
    void setUp() {
        city1 = new City();
        city1.setId(1);
        city1.setName("Test City 1");

        city2 = new City();
        city2.setId(2);
        city2.setName("Test City 2");
    }

    @Test
    void listAll_shouldReturnAllCities() {
        // Arrange
        List<City> expectedCities = Arrays.asList(city1, city2);
        when(cityRepository.findAll()).thenReturn(expectedCities);

        // Act
        List<City> actualCities = cityService.listAll();

        // Assert
        assertEquals(expectedCities, actualCities);
        verify(cityRepository, times(1)).findAll();
    }
}