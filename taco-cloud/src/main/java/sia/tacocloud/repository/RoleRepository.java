package sia.tacocloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sia.tacocloud.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
