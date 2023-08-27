package br.ifnmg.edu.partyrent.modules.places.controllers;

import br.ifnmg.edu.partyrent.modules.places.dtos.CreatePlaceDTO;
import br.ifnmg.edu.partyrent.modules.places.dtos.UpdatePlaceDTO;
import br.ifnmg.edu.partyrent.modules.places.entities.Place;
import br.ifnmg.edu.partyrent.modules.places.services.PlacesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("places")
public class PlacesController {
    @Autowired
    private PlacesService placesService;

    @PostMapping()
    public ResponseEntity<Void> store(@RequestBody() @Valid() CreatePlaceDTO createPlaceDto) {
        this.placesService.store(createPlaceDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    public ResponseEntity<List<Place>> findAll(
            @RequestParam(required = false) BigDecimal price_start,
            @RequestParam(required = false) BigDecimal price_end,
            @RequestParam(required = false) String name
    ) {
        List<Place> places = this.placesService.findAll(price_start, price_end, name);
        return ResponseEntity.ok(places);
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<Place> findOne(@PathVariable() UUID placeId) {
        return ResponseEntity.ok(this.placesService.find(placeId));
    }

    @PutMapping("/{placeId}")
    public ResponseEntity<Place> update(
            @PathVariable() UUID placeId,
            @RequestBody() @Valid() UpdatePlaceDTO updatePlaceDto
    ) {
        return ResponseEntity.ok(this.placesService.update(placeId, updatePlaceDto));
    }

    @DeleteMapping("/{placeId}")
    public ResponseEntity<Void> delete(@PathVariable() UUID placeId) {
        this.placesService.delete(placeId);
        return ResponseEntity.noContent().build();
    }
}
