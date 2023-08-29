package br.ifnmg.edu.partyrent.modules.users.services;

import br.ifnmg.edu.partyrent.modules.addresses.entities.Address;
import br.ifnmg.edu.partyrent.modules.addresses.services.AddressesService;
import br.ifnmg.edu.partyrent.modules.users.dtos.CreateUserDTO;
import br.ifnmg.edu.partyrent.modules.users.dtos.UpdateUserDTO;
import br.ifnmg.edu.partyrent.modules.users.entities.User;
import br.ifnmg.edu.partyrent.modules.users.exceptions.PasswordNotValidException;
import br.ifnmg.edu.partyrent.modules.users.exceptions.UserAlreadyExistsException;
import br.ifnmg.edu.partyrent.modules.users.exceptions.UserNotFoundException;
import br.ifnmg.edu.partyrent.modules.users.repositories.UsersRepository;

import br.ifnmg.edu.partyrent.shared.providers.MailProvider.implementations.JavaMailProvider;
import br.ifnmg.edu.partyrent.shared.utils.Password;
import jakarta.mail.MessagingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AddressesService addressesService;

    @Autowired
    private JavaMailProvider javaMailProvider;

    public void store(CreateUserDTO createUserDto) throws MessagingException {
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

        Address address = this.addressesService.store(createUserDto.address());
        newUser.setAddress(address);

        BeanUtils.copyProperties(createUserDto, newUser);
        String passwordHashed = new BCryptPasswordEncoder().encode(newUser.getPassword());
        newUser.setPassword(passwordHashed);
        newUser.setActivated(false);

        try {
            ClassPathResource resource = new ClassPathResource("email/active_account.html");
            byte[] fileData = FileCopyUtils.copyToByteArray(resource.getFile());

            String activationCode = UUID.randomUUID().toString();

            String htmlTemplate = new String(fileData, StandardCharsets.UTF_8);
            htmlTemplate = htmlTemplate.replace("${name}", newUser.getName());
            htmlTemplate = htmlTemplate.replace("${invitationurl}", "http://localhost:8080/auth/active?token=" + activationCode);

            javaMailProvider.sendMail(
                    newUser.getEmail(),
                    "Ative sua conta!",
                    htmlTemplate
            );

            newUser.setActivationCode(activationCode);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }

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

    public User findByEmail(String email) {
        User user = this.usersRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException();
        }

        return user;
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

        if (updateUserDTO.address() != null) {
            Address address = userToUpdate.getAddress();
            Address addressUpdated = this.addressesService.update(address.getId(), updateUserDTO.address());
            userToUpdate.setAddress(addressUpdated);
        }

        this.usersRepository.save(userToUpdate);

        return userToUpdate;
    }
}
