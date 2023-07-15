package br.ifnmg.edu.partyrent.modules.users.dtos;

public record UpdateUserDTO (
        String name,
        String email,
        String occupation,
        String phone
) { }
