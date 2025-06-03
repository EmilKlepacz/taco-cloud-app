package sia.tacocloud.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sia.tacocloud.model.Taco;

import java.util.Optional;

public interface TacoRepository extends JpaRepository<Taco, Long> {
    Optional<Taco> findByName(String name);

    Page<Taco> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
