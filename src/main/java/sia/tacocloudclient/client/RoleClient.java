package sia.tacocloudclient.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sia.tacocloud.dto.IngredientDTO;
import sia.tacocloud.dto.RoleDTO;
import sia.tacocloudclient.props.ClientProps;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class RoleClient {
    private final RestTemplate restTemplate;
    private final ClientProps props;

    public RoleClient(RestTemplate restTemplate, ClientProps props) {
        this.restTemplate = restTemplate;
        this.props = props;
    }

    public Page<RoleDTO> getAllRoles(int page, int size) {
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("page", page);
        uriVariables.put("size", size);

        URI uri = UriComponentsBuilder
                .fromHttpUrl(props.getBaseUrl() + "/roles?page={page}&size={size}")
                .build(uriVariables);

        RequestEntity<Void> request = RequestEntity
                .get(uri)
                .build();

        ResponseEntity<HelperPage<RoleDTO>> response = restTemplate.exchange(
                request,
                new ParameterizedTypeReference<HelperPage<RoleDTO>>() {}
        );

        return response.getBody();
    }
}
