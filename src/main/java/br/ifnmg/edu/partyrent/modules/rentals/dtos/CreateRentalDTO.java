package br.ifnmg.edu.partyrent.modules.rentals.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CreateRentalDTO(
        UUID place_id,
        List<UUID> service_ids,
        LocalDateTime start_date,
        LocalDateTime end_date
) { }
