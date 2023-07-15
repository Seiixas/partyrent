package br.ifnmg.edu.partyrent.modules.users.repositories;

import br.ifnmg.edu.partyrent.modules.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UsersRepository extends JpaRepository<User, UUID> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findByPhone(String phone);
    public Optional<User> findByCpf(String cpf);
    public Optional<User> findByRg(String rg);
}
