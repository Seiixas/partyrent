package br.ifnmg.edu.partyrent.modules.users.dtos;

import java.time.LocalDate;

public record CreateUserDTO(
        String name,
        String email,
        LocalDate birthday,
        String cpf,
        String rg,
        String occupation,
        String phone
) { }
