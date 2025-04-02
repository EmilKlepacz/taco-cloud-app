package sia.tacocloud.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.support.SessionStatus;
import sia.tacocloud.model.Taco;
import sia.tacocloud.model.TacoOrder;
import sia.tacocloud.repository.OrderRepository;
import sia.tacocloud.repository.TacoRepository;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final TacoRepository tacoRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        TacoRepository tacoRepository) {
        this.orderRepository = orderRepository;
        this.tacoRepository = tacoRepository;
    }

    public void processOrder(@Valid TacoOrder order) {
        orderRepository.save(order);
    }

    public void initOrders() {
        // dummy order 1
        Taco t1 = tacoRepository.findByName("Initial_Taco_1").orElseThrow(() ->
                new RuntimeException("Initial_Taco_1 not found"));
        Taco t2 = tacoRepository.findByName("Initial_Taco_2").orElseThrow(() ->
                new RuntimeException("Initial_Taco_2 not found"));

        TacoOrder order1 = new TacoOrder();
        order1.setDeliveryCity("Some Dummy City");
        order1.setDeliveryName("Some Dummy Name");
        order1.setDeliveryState("Some Dummy State");
        order1.setDeliveryStreet("Some Dummy Street");
        order1.setDeliveryZip("Some Dummy Zip");
        order1.setCcCVV("123");
        order1.setCcNumber("374245455400126");
        order1.setCcExpiration("10/30");
        order1.setTacos(List.of(t1, t2));

        orderRepository.save(order1);

        // dummy order 2
        Taco t3 = tacoRepository.findByName("Initial_Taco_3").orElseThrow(() ->
                new RuntimeException("Initial_Taco_3 not found"));

        TacoOrder order2 = new TacoOrder();
        order2.setDeliveryCity("Some Dummy City");
        order2.setDeliveryName("Some Dummy Name");
        order2.setDeliveryState("Some Dummy State");
        order2.setDeliveryStreet("Some Dummy Street");
        order2.setDeliveryZip("Some Dummy Zip");
        order2.setCcCVV("123");
        order2.setCcNumber("374245455400126");
        order2.setCcExpiration("10/30");
        order2.setTacos(List.of(t3));

        orderRepository.save(order2);
    }
}
