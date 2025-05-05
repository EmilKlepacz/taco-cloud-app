package sia.tacocloudclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import sia.tacocloud.dto.IngredientDTO;
import sia.tacocloud.dto.TacoOrderDTO;
import sia.tacocloudclient.client.IngredientClient;
import sia.tacocloudclient.client.OrderClient;

@SpringBootApplication
public class TacoCloudClientApplication {

    private static final Logger log = LoggerFactory.getLogger(TacoCloudClientApplication.class);
    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudClientApplication.class, args);
    }

    @Bean
    @Profile("dev")
    public CommandLineRunner executeAPICalls(IngredientClient ingredientClient,
                                             OrderClient orderClient) {
        return args -> {
            //some dummy tests to check connection between backend server
            // Ingredient API
            try {
                IngredientDTO ingredientDTO = ingredientClient.getIngredient("TMTO");
                log.info("\nFetched ingredient: {}", mapper.writeValueAsString(ingredientDTO));
            } catch (Exception e) {
                log.error("Failed to fetch ingredient", e);
            }

            try {
                Page<IngredientDTO> ingredientDTOS = ingredientClient.getAllIngredients(0, 12);
                log.info("\nFetched ingredients: {}", mapper.writeValueAsString(ingredientDTOS.getContent()));
            } catch (Exception e) {
                log.error("Failed to fetch ingredients", e);
            }

            // Order API
            try {
                TacoOrderDTO toUpdate = new TacoOrderDTO();
                toUpdate.setDeliveryName("Test Delivery");
                toUpdate.setDeliveryCity("Test City");
                toUpdate.setDeliveryState("Test State");
                toUpdate.setDeliveryZip("Test Zip");
                toUpdate.setDeliveryStreet("Test Street");

                TacoOrderDTO updated = orderClient.updateOrder(1L, toUpdate);
                log.info("\nUpdated order: {}", mapper.writeValueAsString(updated));
            } catch (Exception e) {
                log.error("Failed to update order", e);
            }

            try {
                TacoOrderDTO toPatch = new TacoOrderDTO();
                toPatch.setDeliveryName("Test Patch Delivery Name");

                TacoOrderDTO updated = orderClient.patchOrder(2L, toPatch);
                log.info("\nPatched order: {}", mapper.writeValueAsString(updated));
            } catch (Exception e) {
                log.error("Failed to patch order", e);
            }

            try {
                orderClient.deleteOrder(99L);
                log.info("Order with ID 99 deleted successfully.");
            } catch (Exception e) {
                log.error("Failed to delete order", e);
            }
        };


    }
}