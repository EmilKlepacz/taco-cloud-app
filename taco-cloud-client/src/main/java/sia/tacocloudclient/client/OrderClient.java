package sia.tacocloudclient.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sia.tacocloud.dto.IngredientDTO;
import sia.tacocloud.dto.TacoOrderDTO;
import sia.tacocloudclient.props.ClientProps;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class OrderClient {
    private final RestTemplate restTemplate;
    private final ClientProps props;

    public OrderClient(RestTemplate restTemplate, ClientProps props) {
        this.restTemplate = restTemplate;
        this.props = props;
    }

    public TacoOrderDTO updateOrder(Long orderId, TacoOrderDTO orderDto) {
        return sendOrderRequest(orderId, orderDto, HttpMethod.PUT);
    }

    public TacoOrderDTO patchOrder(Long orderId, TacoOrderDTO orderDto) {
        return sendOrderRequest(orderId, orderDto, HttpMethod.PATCH);
    }

    private TacoOrderDTO sendOrderRequest(Long orderId, TacoOrderDTO orderDto, HttpMethod method) {
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("orderId", orderId);

        URI uri = UriComponentsBuilder
                .fromHttpUrl(props.getBaseUrl() + "/orders/{orderId}")
                .build(uriVariables);

        RequestEntity<TacoOrderDTO> request = RequestEntity
                .method(method, uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(orderDto);

        ResponseEntity<TacoOrderDTO> response = restTemplate.exchange(
                request,
                TacoOrderDTO.class
        );

        return response.getBody();
    }

    public Void deleteOrder(Long orderId) {
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("orderId", orderId);

        URI uri = UriComponentsBuilder
                .fromHttpUrl(props.getBaseUrl() + "/orders/{orderId}")
                .build(uriVariables);

        RequestEntity<Void> request = RequestEntity
                .delete(uri)
                .build();

        ResponseEntity<Void> response = restTemplate.exchange(
                request,
                Void.class
        );

        return response.getBody();
    }
}