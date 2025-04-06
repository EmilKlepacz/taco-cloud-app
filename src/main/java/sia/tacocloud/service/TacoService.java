package sia.tacocloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sia.tacocloud.model.Ingredient;
import sia.tacocloud.model.Taco;
import sia.tacocloud.repository.IngredientRepository;
import sia.tacocloud.repository.TacoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TacoService {
    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;

    @Autowired
    public TacoService(TacoRepository tacoRepository,
                       IngredientRepository ingredientRepository) {
        this.tacoRepository = tacoRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public void saveTaco(Taco taco) {
        tacoRepository.save(taco);
    }

    public void initTacos() {
        // use already added ingredients in IngredientService.initIngredients()
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findById("FLTO").ifPresent(ingredient -> ingredients.add(ingredient));
        ingredientRepository.findById("COTO").ifPresent(ingredient -> ingredients.add(ingredient));
        ingredientRepository.findById("GRBF").ifPresent(ingredient -> ingredients.add(ingredient));
        ingredientRepository.findById("CARN").ifPresent(ingredient -> ingredients.add(ingredient));
        ingredientRepository.findById("TMTO").ifPresent(ingredient -> ingredients.add(ingredient));

        Taco t1 = new Taco();
        t1.setName("Initial_Taco_1");
        t1.setIngredients(ingredients);
        tacoRepository.save(t1);

        Taco t2 = new Taco();
        t2.setName("Initial_Taco_2");
        t2.setIngredients(ingredients);
        tacoRepository.save(t2);

        Taco t3 = new Taco();
        t3.setName("Initial_Taco_3");
        t3.setIngredients(ingredients);
        tacoRepository.save(t3);

        // many records for some testing of paging
        for (int i = 1; i <= 100; i++) {
            Taco taco = new Taco();
            taco.setName("Paging_Test_Taco_" + i);
            taco.setIngredients(ingredients);
            tacoRepository.save(taco);
        }
    }
}
