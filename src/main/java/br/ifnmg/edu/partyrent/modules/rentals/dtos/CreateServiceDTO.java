package br.ifnmg.edu.partyrent.modules.rentals.dtos;

import java.math.BigDecimal;

public record CreateServiceDTO (
        String name,
        String description,
        BigDecimal price
) { }
