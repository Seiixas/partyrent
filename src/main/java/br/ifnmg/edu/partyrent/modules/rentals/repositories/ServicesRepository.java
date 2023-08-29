package br.ifnmg.edu.partyrent.modules.rentals.repositories;

import br.ifnmg.edu.partyrent.modules.rentals.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ServicesRepository extends JpaRepository<Service, UUID> {
    Optional<Service> findByName(String name);
}
