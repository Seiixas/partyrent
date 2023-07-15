package br.ifnmg.edu.partyrent.modules.users.services;

import br.ifnmg.edu.partyrent.modules.users.dtos.CreateUserDTO;
import br.ifnmg.edu.partyrent.modules.users.dtos.UpdateUserDTO;
import br.ifnmg.edu.partyrent.modules.users.entities.User;
import br.ifnmg.edu.partyrent.modules.users.exceptions.UserAlreadyExistsException;
import br.ifnmg.edu.partyrent.modules.users.exceptions.UserNotFoundException;
import br.ifnmg.edu.partyrent.modules.users.repositories.UsersRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    UsersRepository usersRepository;

    public void store(CreateUserDTO createUserDto) {
        Optional<User> userExists = this.usersRepository.findByEmail(createUserDto.email());

        if (userExists.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        userExists = this.usersRepository.findByCpf(createUserDto.cpf());

        if (userExists.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        userExists = this.usersRepository.findByRg((createUserDto.rg()));

        if (userExists.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        userExists = this.usersRepository.findByPhone((createUserDto.rg()));

        if (userExists.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        User user = new User();
        BeanUtils.copyProperties(createUserDto, user);

        this.usersRepository.save(user);
    }

    public List<User> all() {
        return this.usersRepository.findAll();
    }

    public User find(UUID userId) {
        Optional<User> user = this.usersRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        return user.get();
    }

    public void delete(UUID userId) {
        Optional<User> user = this.usersRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        this.usersRepository.delete(user.get());
    }

    public User update(UUID userId, UpdateUserDTO updateUserDTO) {
        Optional<User> user = this.usersRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        User userToUpdate = user.get();

        BeanUtils.copyProperties(updateUserDTO, userToUpdate);

        this.usersRepository.save(userToUpdate);

        return userToUpdate;
    }
}
