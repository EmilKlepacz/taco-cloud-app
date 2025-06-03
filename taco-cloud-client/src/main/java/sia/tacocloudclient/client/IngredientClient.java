package sia.tacocloudclient.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sia.tacocloud.dto.IngredientDTO;
import sia.tacocloudclient.props.ClientProps;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class IngredientClient {
    private final RestTemplate restTemplate;
    private final ClientProps props;

    public IngredientClient(ClientProps props,
                            RestTemplate restTemplate) {
        this.props = props;
        this.restTemplate = restTemplate;
    }

    public IngredientDTO getIngredient(String ingredientId) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("ingredientId", ingredientId);

        URI uri = UriComponentsBuilder
                .fromHttpUrl(props.getBaseUrl() + "/ingredients/{ingredientId}")
                .build(uriVariables);

        RequestEntity<Void> request = RequestEntity
                .get(uri)
                .build();

        ResponseEntity<IngredientDTO> response = restTemplate.exchange(
                request,
                IngredientDTO.class
        );

        return response.getBody();
    }

    public Page<IngredientDTO> getAllIngredients(int page, int size) {
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("page", page);
        uriVariables.put("size", size);

        URI uri = UriComponentsBuilder
                .fromHttpUrl(props.getBaseUrl() + "/ingredients?page={page}&size={size}")
                .build(uriVariables);

        RequestEntity<Void> request = RequestEntity
                .get(uri)
                .build();

        ResponseEntity<HelperPage<IngredientDTO>> response = restTemplate.exchange(
                request,
                new ParameterizedTypeReference<HelperPage<IngredientDTO>>() {}
        );

        return response.getBody();
    }
}
