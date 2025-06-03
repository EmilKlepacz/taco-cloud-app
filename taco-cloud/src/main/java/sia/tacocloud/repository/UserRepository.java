package sia.tacocloud.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import sia.tacocloud.model.AppUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    @EntityGraph(attributePaths = "roles") // Forces fetching roles when retrieving users
    Optional<AppUser> findByUsername(String username);
}
