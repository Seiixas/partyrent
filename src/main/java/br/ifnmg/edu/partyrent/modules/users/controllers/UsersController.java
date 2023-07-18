package br.ifnmg.edu.partyrent.modules.users.controllers;

import br.ifnmg.edu.partyrent.modules.users.dtos.CreateUserDTO;
import br.ifnmg.edu.partyrent.modules.users.dtos.UpdateUserDTO;
import br.ifnmg.edu.partyrent.modules.users.entities.User;

import br.ifnmg.edu.partyrent.modules.users.services.UsersService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    UsersService usersService;
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.all());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findOne(@PathVariable() UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.usersService.find(userId));
    }

    @PostMapping
    public ResponseEntity<Void> store(@RequestBody() @Valid CreateUserDTO createUserDto) throws MessagingException {
        this.usersService.store(createUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable() UUID userId) {
        this.usersService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> update(@PathVariable() UUID userId, @RequestBody() @Valid UpdateUserDTO updateUserDto) {
        User user = this.usersService.update(userId, updateUserDto);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
