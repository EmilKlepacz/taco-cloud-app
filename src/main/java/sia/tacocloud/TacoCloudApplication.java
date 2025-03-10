package sia.tacocloud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sia.tacocloud.model.Ingredient;
import sia.tacocloud.repository.IngredientRepository;
import sia.tacocloud.service.IngredientService;
import sia.tacocloud.service.UserService;

@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientService ingredientService, UserService userService) {
        return args -> {
            ingredientService.initIngredients();
            userService.initUsers();
        };
    }

}
