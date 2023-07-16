package br.ifnmg.edu.partyrent.modules.users.services;

import br.ifnmg.edu.partyrent.modules.users.dtos.LoginDTO;
import br.ifnmg.edu.partyrent.modules.users.dtos.LoginResponseDTO;
import br.ifnmg.edu.partyrent.modules.users.entities.User;
import br.ifnmg.edu.partyrent.modules.users.repositories.UsersRepository;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginResponseDTO login(LoginDTO loginDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var user = (User) auth.getPrincipal();

        return new LoginResponseDTO(
                user.getId(),
                LocalDateTime.now().plusHours(2),
                this.generateToken(user)
        );
    }

    private String generateToken(User user) {
        return JWT
                .create()
                .withSubject(user.getEmail())
                .withClaim("id", user.getId().toString())
                .withExpiresAt(
                        LocalDateTime
                                .now()
                                .plusHours(2)
                                .toInstant(ZoneOffset.of("-03:00"))
                )
                .sign(Algorithm.HMAC256("password-secret"));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.usersRepository.findByEmail(username);
    }

    public String getSubject(String token) {
        return JWT
                .require(Algorithm.HMAC256("password-secret"))
                .build()
                .verify(token)
                .getSubject();
    }
}
