package br.ifnmg.edu.partyrent.modules.users.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank String email,
        @NotBlank String password
) {
}
