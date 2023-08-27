package br.ifnmg.edu.partyrent.modules.addresses.repositories;

import br.ifnmg.edu.partyrent.modules.addresses.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressesRepository extends JpaRepository<Address, UUID> {
}
