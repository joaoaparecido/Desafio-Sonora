package com.desafio.produtos.repositories;

import com.desafio.produtos.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Integer> {
}