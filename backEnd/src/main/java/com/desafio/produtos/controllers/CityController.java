package com.desafio.produtos.controllers;

import com.desafio.produtos.domain.City;
import com.desafio.produtos.dto.CityDTO;
import com.desafio.produtos.dto.CityMapper;
import com.desafio.produtos.services.CityService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cidades")
public class CityController {

    private final CityService cityService;
    private final CityMapper cityMapper;

    public CityController(CityService cityService, CityMapper cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    @GetMapping
    public List<CityDTO> listAll(@Parameter(description = "UF do estado (opcional)")
                                     @RequestParam(required = false) String uf
    ) {
        List<City> cities;

        if (uf != null && !uf.isEmpty()) {
            cities = cityService.findByUf(uf.toUpperCase());
        } else {
            cities = cityService.listAll();
        }

        return cities.stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());

    }
}