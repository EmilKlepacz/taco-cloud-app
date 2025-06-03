package sia.tacocloudclient.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sia.tacocloud.dto.RoleDTO;
import sia.tacocloud.dto.TacoDTO;
import sia.tacocloudclient.props.ClientProps;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class TacoClient {
    private final RestTemplate restTemplate;
    private final ClientProps props;

    public TacoClient(RestTemplate restTemplate, ClientProps props) {
        this.restTemplate = restTemplate;
        this.props = props;
    }

    public Page<TacoDTO> getRecentTacos(int page, int size) {
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("page", page);
        uriVariables.put("size", size);

        URI uri = UriComponentsBuilder
                .fromHttpUrl(props.getBaseUrl() + "/tacos?recent&page={page}&size={size}")
                .build(uriVariables);

        RequestEntity<Void> request = RequestEntity
                .get(uri)
                .build();

        ResponseEntity<HelperPage<TacoDTO>> response = restTemplate.exchange(
                request,
                new ParameterizedTypeReference<HelperPage<TacoDTO>>() {
                }
        );

        return response.getBody();
    }

    public TacoDTO getTacoById(long id) {
        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("id", id);

        URI uri = UriComponentsBuilder
                .fromHttpUrl(props.getBaseUrl() + "/tacos/{id}")
                .build(uriVariables);

        RequestEntity<Void> request = RequestEntity
                .get(uri)
                .build();

        ResponseEntity<TacoDTO> response = restTemplate.exchange(
                request,
                TacoDTO.class
        );

        return response.getBody();
    }

    public TacoDTO createTaco(TacoDTO tacoDTO) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(props.getBaseUrl() + "/tacos")
                .build()
                .toUri();

        RequestEntity<TacoDTO> request = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(tacoDTO);

        ResponseEntity<TacoDTO> response = restTemplate.exchange(
                request,
                TacoDTO.class
        );

        return response.getBody();
    }
}

