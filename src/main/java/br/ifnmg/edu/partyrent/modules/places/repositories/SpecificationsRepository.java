package br.ifnmg.edu.partyrent.modules.places.repositories;


import br.ifnmg.edu.partyrent.modules.places.entities.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpecificationsRepository extends JpaRepository<Specification, UUID> {
    Specification findByName(String name);
}
