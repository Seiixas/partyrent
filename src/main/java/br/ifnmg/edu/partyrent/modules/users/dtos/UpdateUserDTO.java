package br.ifnmg.edu.partyrent.modules.users.dtos;

import br.ifnmg.edu.partyrent.modules.addresses.dtos.UpdateAddressDTO;
import jakarta.validation.constraints.Email;

public record UpdateUserDTO(
        String name,
        @Email String email,
        String occupation,
        String phone,
        UpdateAddressDTO address
) { }
