package sia.tacocloud.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sia.tacocloud.model.AppUser;
import sia.tacocloud.model.TacoOrder;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<TacoOrder, Long> {
    List<TacoOrder> findByUserOrderByPlacedAtDesc(AppUser user, Pageable pageable);

    Optional<TacoOrder> findById(Long id);
}
