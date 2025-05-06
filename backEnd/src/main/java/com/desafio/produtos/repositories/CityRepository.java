package com.desafio.produtos.repositories;

import com.desafio.produtos.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findByUfOrderByName(String uf);
}