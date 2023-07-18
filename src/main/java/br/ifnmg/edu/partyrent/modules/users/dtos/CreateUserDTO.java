package br.ifnmg.edu.partyrent.modules.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateUserDTO(
        String name,
        String password,
        String email,
        LocalDate birthday,
        String cpf,
        String rg,
        String occupation,
        String phone
) { }
