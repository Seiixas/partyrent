package br.ifnmg.edu.partyrent.modules.places.dtos;

import br.ifnmg.edu.partyrent.modules.addresses.dtos.UpdateAddressDTO;

import java.math.BigDecimal;

public record UpdatePlaceDTO (
    String name,
    String description,
    Integer capacity,
    BigDecimal price,
    UpdateAddressDTO address
) { }
