package sia.auth.tacocloudauthserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sia.auth.tacocloudauthserver.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
