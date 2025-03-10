package sia.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
