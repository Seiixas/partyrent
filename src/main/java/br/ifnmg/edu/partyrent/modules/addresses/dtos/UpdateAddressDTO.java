package br.ifnmg.edu.partyrent.modules.addresses.dtos;

public record UpdateAddressDTO (
        String street,

        String district,

        String city,

        String state,

        String complement,

        String number,

        double latitude,

        double longitude,

        String cep
) { }
