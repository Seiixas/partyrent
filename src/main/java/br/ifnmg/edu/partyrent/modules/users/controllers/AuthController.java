package br.ifnmg.edu.partyrent.modules.users.controllers;

import br.ifnmg.edu.partyrent.modules.users.dtos.LoginDTO;
import br.ifnmg.edu.partyrent.modules.users.dtos.LoginResponseDTO;
import br.ifnmg.edu.partyrent.modules.users.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO loginDto) {
        LoginResponseDTO loginResponse = this.authService.login(loginDto);
        return ResponseEntity.status(200).body(loginResponse);
    }

    @GetMapping("/active")
    public ResponseEntity<String> active(@RequestParam(name="token") String token) {
        return ResponseEntity.ok(this.authService.activateUser(token));
    }
}
