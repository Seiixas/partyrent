package br.ifnmg.edu.partyrent.modules.places.dtos;

import br.ifnmg.edu.partyrent.modules.addresses.dtos.CreateAddressDTO;

import java.math.BigDecimal;

public record CreatePlaceDTO (
        String name,
        String description,
        Integer capacity,
        BigDecimal price,
        CreateAddressDTO address
) { }
