package sia.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.model.Role;
import sia.tacocloud.model.Taco;

import java.util.Optional;

public interface TacoRepository extends CrudRepository<Taco, Long> {
    Optional<Taco> findByName(String name);
}
