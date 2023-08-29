package br.ifnmg.edu.partyrent.modules.rentals.repositories;

import br.ifnmg.edu.partyrent.modules.rentals.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RentalsRepository extends JpaRepository<Rental, UUID> {
    @Query("SELECT r FROM Rental r " +
            "WHERE r.start_date <= :endDate AND r.end_date >= :startDate")
    List<Rental> findAvailability(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
