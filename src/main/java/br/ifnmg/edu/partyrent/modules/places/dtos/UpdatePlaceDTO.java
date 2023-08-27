package br.ifnmg.edu.partyrent.modules.places.dtos;

import java.math.BigDecimal;

public record UpdatePlaceDTO (
    String name,
    String description,
    Integer capacity,
    BigDecimal price
) {}
