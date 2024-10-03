package sia.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import sia.tacocloud.model.Ingredient;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
