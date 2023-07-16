package br.ifnmg.edu.partyrent.modules.users.services;

import br.ifnmg.edu.partyrent.modules.users.dtos.CreateUserDTO;
import br.ifnmg.edu.partyrent.modules.users.dtos.UpdateUserDTO;
import br.ifnmg.edu.partyrent.modules.users.entities.User;
import br.ifnmg.edu.partyrent.modules.users.exceptions.PasswordNotValidException;
import br.ifnmg.edu.partyrent.modules.users.exceptions.UserAlreadyExistsException;
import br.ifnmg.edu.partyrent.modules.users.exceptions.UserNotFoundException;
import br.ifnmg.edu.partyrent.modules.users.repositories.UsersRepository;

import br.ifnmg.edu.partyrent.shared.utils.Password;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public void store(CreateUserDTO createUserDto) {
        ArrayList<String> passwordErrors = Password.isValid((createUserDto.password()));

        if (passwordErrors.size() > 0) {
            throw new PasswordNotValidException(passwordErrors);
        }

        User user = this.usersRepository.findByEmail(createUserDto.email());

        if (user != null) {
            throw new UserAlreadyExistsException();
        }

        user = this.usersRepository.findByCpf(createUserDto.cpf());

        if (user != null) {
            throw new UserAlreadyExistsException();
        }

        user = this.usersRepository.findByRg((createUserDto.rg()));

        if (user != null) {
            throw new UserAlreadyExistsException();
        }

        user = this.usersRepository.findByPhone((createUserDto.rg()));

        if (user != null) {
            throw new UserAlreadyExistsException();
        }

        User newUser = new User();

        BeanUtils.copyProperties(createUserDto, newUser);
        String passwordHashed = new BCryptPasswordEncoder().encode(newUser.getPassword());
        newUser.setPassword(passwordHashed);

        this.usersRepository.save(newUser);
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
