package com.desafio.produtos.services;

import com.desafio.produtos.domain.City;
import com.desafio.produtos.domain.Uf;
import com.desafio.produtos.exceptions.InvalidUfException;
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

    public List<City> findByUf(String uf) {
        if (!Uf.isValid(uf)) {
            throw new InvalidUfException("Invalid UF: " + uf);
        }
        return cityRepository.findByUfOrderByName(uf);
    }
}