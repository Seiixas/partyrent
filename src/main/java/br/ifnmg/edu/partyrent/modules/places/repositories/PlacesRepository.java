package br.ifnmg.edu.partyrent.modules.places.repositories;

import br.ifnmg.edu.partyrent.modules.places.entities.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface PlacesRepository extends JpaRepository<Place, UUID> {
    @Query("SELECT p FROM Place p " +
            "WHERE (:name IS NULL OR p.name LIKE %:name%) " +
            "AND (:price_start IS NULL OR p.price >= :price_start) " +
            "AND (:price_end IS NULL OR p.price <= :price_end) " +
            "ORDER BY p.price ASC, p.name ASC")
    List<Place> findAllFilteredAndSorted(
            @Param("price_start") BigDecimal price_start,
            @Param("price_end") BigDecimal price_end,
            @Param("name") String name,
            Pageable pageable
    );
}
