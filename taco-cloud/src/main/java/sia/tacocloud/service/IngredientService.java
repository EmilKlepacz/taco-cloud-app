package sia.tacocloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import sia.tacocloud.model.Ingredient;
import sia.tacocloud.repository.IngredientRepository;
import sia.tacocloud.model.enums.IngredientType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Optional<Ingredient> getIngredientById(String id) {
        return ingredientRepository.findById(id);
    }

    public Page<Ingredient> getAllIngredients(Pageable pageable) {
        return ingredientRepository.findAll(pageable);
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, IngredientType type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredient -> ingredients.add(ingredient));

        IngredientType[] types = IngredientType.values();
        for (IngredientType type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public void deleteIngredientById(String ingredientId) {
        try {
            ingredientRepository.deleteById(ingredientId);
        } catch (EmptyResultDataAccessException e) {
            //do nothing about it
        }
    }

    public void initIngredients() {
        if (ingredientRepository.count() == 0) {
            ingredientRepository.saveAll(List.of(
                    new Ingredient("FLTO", "Flour Tortilla", IngredientType.WRAP),
                    new Ingredient("COTO", "Corn Tortilla", IngredientType.WRAP),
                    new Ingredient("GRBF", "Ground Beef", IngredientType.PROTEIN),
                    new Ingredient("CARN", "Carnitas", IngredientType.PROTEIN),
                    new Ingredient("TMTO", "Diced Tomatoes", IngredientType.VEGGIES),
                    new Ingredient("LETC", "Lettuce", IngredientType.VEGGIES),
                    new Ingredient("CHED", "Cheddar", IngredientType.CHEESE),
                    new Ingredient("JACK", "Monterrey Jack", IngredientType.CHEESE),
                    new Ingredient("SLSA", "Salsa", IngredientType.SAUCE),
                    new Ingredient("SRCR", "Sour Cream", IngredientType.SAUCE)
            ));
        }
    }
}
