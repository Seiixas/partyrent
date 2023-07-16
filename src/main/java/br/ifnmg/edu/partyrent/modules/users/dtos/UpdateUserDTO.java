package br.ifnmg.edu.partyrent.modules.users.dtos;

import jakarta.validation.constraints.Email;

public record UpdateUserDTO(
        String name,
        @Email String email,
        String occupation,
        String phone
) { }
