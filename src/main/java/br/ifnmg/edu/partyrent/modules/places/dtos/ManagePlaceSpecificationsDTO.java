package br.ifnmg.edu.partyrent.modules.places.dtos;

import java.util.List;
import java.util.UUID;

public record ManagePlaceSpecificationsDTO (
    List<UUID> specification_ids
) { }
