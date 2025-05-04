package sia.tacocloudclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import sia.tacocloud.dto.IngredientDTO;
import sia.tacocloudclient.client.IngredientClient;

@SpringBootApplication
public class TacoCloudClientApplication {

    private static final Logger log = LoggerFactory.getLogger(TacoCloudClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudClientApplication.class, args);
    }

    @Bean
    @Profile("dev")
    public CommandLineRunner executeAPICalls(IngredientClient ingredientClient) {
        return args -> {
            //some dummy test to check connection between backend server
            try {
                IngredientDTO ingredientDTO = ingredientClient.getIngredient("TMTO");
                log.info("Fetched ingredient: {}", ingredientDTO);
            } catch (Exception e) {
                log.error("Failed to fetch ingredient", e);
            }
        };
    }
}