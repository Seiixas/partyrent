package br.ifnmg.edu.partyrent.modules.places.controllers;

import br.ifnmg.edu.partyrent.modules.places.dtos.CreateSpecificationDTO;
import br.ifnmg.edu.partyrent.modules.places.dtos.UpdateSpecificationDTO;
import br.ifnmg.edu.partyrent.modules.places.entities.Specification;
import br.ifnmg.edu.partyrent.modules.places.services.SpecificationsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("specifications")
public class SpecificationsController {
    @Autowired
    private SpecificationsService specificationsService;

    @PostMapping()
    public ResponseEntity<Void> store(@RequestBody() @Valid() CreateSpecificationDTO createSpecificationDto) {
        this.specificationsService.store(createSpecificationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{specificationId}")
    public ResponseEntity<Specification> update(
            @PathVariable() UUID specificationId,
            @RequestBody() @Valid() UpdateSpecificationDTO updateSpecificationDto
    ) {
        return ResponseEntity.ok(this.specificationsService.update(specificationId, updateSpecificationDto));
    }

    @GetMapping("/{specificationId}")
    public ResponseEntity<Specification> findOne(@PathVariable() UUID specificationId) {
        return ResponseEntity.ok(this.specificationsService.find(specificationId));
    }

    @GetMapping()
    public ResponseEntity<List<Specification>> findAll() {
        return ResponseEntity.ok(this.specificationsService.findAll());
    }

    @DeleteMapping("/{specificationId}")
    public ResponseEntity<Void> delete(@PathVariable() UUID specificationId) {
        this.specificationsService.delete(specificationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
