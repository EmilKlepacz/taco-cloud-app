package sia.tacocloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sia.tacocloud.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {
}
