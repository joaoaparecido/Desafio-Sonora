package com.desafio.produtos.dto;

import com.desafio.produtos.domain.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    public CityDTO toDto(City entity) {
        if (entity == null) {
            return null;
        }

        CityDTO dto = new CityDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setUf(entity.getUf());

        return dto;
    }
}