package sia.tacocloud.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.model.AppUser;

import java.util.Optional;

public interface UserRepository extends CrudRepository<AppUser, Long> {
    @EntityGraph(attributePaths = "roles") // Forces fetching roles when retrieving users
    Optional<AppUser> findByUsername(String username);
}
