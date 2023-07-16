package br.ifnmg.edu.partyrent.modules.users.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateUserDTO(
        @NotBlank String name,
        @NotBlank @Min(6) String password,
        @NotBlank @Email String email,
        @NotBlank LocalDate birthday,
        @NotBlank String cpf,
        @NotBlank String rg,
        @NotBlank String occupation,
        @NotBlank String phone
) { }
