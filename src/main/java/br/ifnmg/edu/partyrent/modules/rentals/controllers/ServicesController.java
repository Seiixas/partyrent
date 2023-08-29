package br.ifnmg.edu.partyrent.modules.rentals.controllers;

import br.ifnmg.edu.partyrent.modules.rentals.dtos.CreateServiceDTO;
import br.ifnmg.edu.partyrent.modules.rentals.dtos.UpdateServiceDTO;
import br.ifnmg.edu.partyrent.modules.rentals.entities.Service;
import br.ifnmg.edu.partyrent.modules.rentals.services.ServicesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("services")
public class ServicesController {
    @Autowired
    private ServicesService servicesService;

    @PostMapping()
    public ResponseEntity<Void> store(@RequestBody() @Valid() CreateServiceDTO createServiceDto) {
        this.servicesService.store(createServiceDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<Service> update(
            @PathVariable() UUID serviceId,
            @RequestBody() @Valid() UpdateServiceDTO updateServiceDto
    ) {
        return ResponseEntity.ok(this.servicesService.update(serviceId, updateServiceDto));
    }

    @GetMapping()
    public ResponseEntity<List<Service>> findAll() {
        return ResponseEntity.ok(this.servicesService.findAll());
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<Service> findOne(@PathVariable() UUID serviceId) {
        return ResponseEntity.ok(this.servicesService.find(serviceId));
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> delete(@PathVariable() UUID serviceId) {
        this.servicesService.delete(serviceId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
