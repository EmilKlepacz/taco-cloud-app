package sia.tacocloudclient.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sia.tacocloud.dto.IngredientDTO;
import sia.tacocloudclient.props.IngredientClientProps;

@Component
public class IngredientClient {
    private final RestTemplate restTemplate;
    private final IngredientClientProps props;

    public IngredientClient(IngredientClientProps props,
                            RestTemplate restTemplate) {
        this.props = props;
        this.restTemplate = restTemplate;
    }

    public IngredientDTO getIngredient(String id) {
        return restTemplate.getForObject(props.getBaseUrl() + "/ingredients/{id}",
                IngredientDTO.class, id);

    }
}
