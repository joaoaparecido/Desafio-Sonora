package com.desafio.produtos.controllers;

import com.desafio.produtos.dto.CityDTO;
import com.desafio.produtos.dto.CityMapper;
import com.desafio.produtos.services.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<CityDTO> listAll() {
        return cityService.listAll().stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
    }
}