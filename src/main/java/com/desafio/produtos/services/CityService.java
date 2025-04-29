package com.desafio.produtos.services;

import com.desafio.produtos.domain.City;
import com.desafio.produtos.repositories.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> listAll() {
        return cityRepository.findAll();
    }
}