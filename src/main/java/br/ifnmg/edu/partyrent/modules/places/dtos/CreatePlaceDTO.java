package br.ifnmg.edu.partyrent.modules.places.dtos;

import java.math.BigDecimal;

public record CreatePlaceDTO (
        String name,
        String description,
        Integer capacity,
        BigDecimal price
) { }
