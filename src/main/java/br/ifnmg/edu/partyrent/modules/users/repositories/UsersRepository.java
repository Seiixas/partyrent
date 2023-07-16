package br.ifnmg.edu.partyrent.modules.users.repositories;

import br.ifnmg.edu.partyrent.modules.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UsersRepository extends JpaRepository<User, UUID> {
    public User findByEmail(String email);
    public User findByPhone(String phone);
    public User findByCpf(String cpf);
    public User findByRg(String rg);
}
