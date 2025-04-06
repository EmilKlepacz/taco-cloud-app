package sia.tacocloud.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.model.TacoOrder;
import sia.tacocloud.service.OrderService;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/orders",
        produces = "application/json")
@CrossOrigin(origins = "http://tacocloud:8080")
public class OrderRestController {
    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping(path = "/{orderId}", consumes = "application/json")
    public TacoOrder updateOrder(@PathVariable("orderId") Long orderId,
                                 @RequestBody TacoOrder order) {
        order.setId(orderId);
        return orderService.updateOrder(orderId, order);
    }

    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public ResponseEntity<TacoOrder> patchOrder(@PathVariable("orderId") Long orderId,
                                                @RequestBody TacoOrder patch) {
        Optional<TacoOrder> tacoOrderOpt = orderService.getOrderById(orderId);
        if (tacoOrderOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TacoOrder tacoOrder = tacoOrderOpt.get();
        if (patch.getDeliveryName() != null) {
            tacoOrder.setDeliveryName(patch.getDeliveryName());
        }
        if (patch.getDeliveryStreet() != null) {
            tacoOrder.setDeliveryStreet(patch.getDeliveryStreet());
        }
        if (patch.getDeliveryCity() != null) {
            tacoOrder.setDeliveryCity(patch.getDeliveryCity());
        }
        if (patch.getDeliveryState() != null) {
            tacoOrder.setDeliveryState(patch.getDeliveryState());
        }
        if (patch.getDeliveryZip() != null) {
            tacoOrder.setDeliveryZip(patch.getDeliveryZip());
        }
        if (patch.getCcNumber() != null) {
            tacoOrder.setCcNumber(patch.getCcNumber());
        }
        if (patch.getCcExpiration() != null) {
            tacoOrder.setCcExpiration(patch.getCcExpiration());
        }
        if (patch.getCcCVV() != null) {
            tacoOrder.setCcCVV(patch.getCcCVV());
        }

        // Optionally handle tacos if allowed to update:
        if (patch.getTacos() != null && !patch.getTacos().isEmpty()) {
            //todo
        }

        TacoOrder patchedOrder = orderService.saveOrder(tacoOrder);
        return new ResponseEntity<>(patchedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        orderService.deleteOrderById(orderId);
    }
}
