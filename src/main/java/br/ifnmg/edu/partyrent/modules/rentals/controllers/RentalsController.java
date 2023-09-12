package br.ifnmg.edu.partyrent.modules.rentals.controllers;

import br.ifnmg.edu.partyrent.modules.rentals.dtos.CreateRentalDTO;
import br.ifnmg.edu.partyrent.modules.rentals.entities.Rental;
import br.ifnmg.edu.partyrent.modules.rentals.services.RentalsService;
import br.ifnmg.edu.partyrent.modules.users.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("rentals")
public class RentalsController {
    @Autowired
    private RentalsService rentalsService;

    @Autowired
    private AuthService authService;

    @PostMapping()
    public ResponseEntity<Rental> create(
            @RequestBody() @Valid() CreateRentalDTO createRentalDto,
            @RequestHeader (name="Authorization") String token
    ) {
        String userEmail = this.authService.getSubject(token);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.rentalsService.create(createRentalDto, userEmail));
    }
}
