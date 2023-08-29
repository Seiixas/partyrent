package br.ifnmg.edu.partyrent.modules.rentals.dtos;

import java.math.BigDecimal;

public record UpdateServiceDTO (
        String name,
        String description,
        BigDecimal price
) { }
