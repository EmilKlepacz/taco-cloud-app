package sia.tacocloud.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import sia.tacocloud.model.AppUser;
import sia.tacocloud.model.Taco;
import sia.tacocloud.model.TacoOrder;
import sia.tacocloud.repository.OrderRepository;
import sia.tacocloud.repository.TacoRepository;
import sia.tacocloud.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final TacoRepository tacoRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        TacoRepository tacoRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.tacoRepository = tacoRepository;
        this.userRepository = userRepository;
    }

    public TacoOrder saveOrder(@Valid TacoOrder order) {
        return orderRepository.save(order);
    }

    public List<TacoOrder> ordersForUser(@AuthenticationPrincipal AppUser user, Pageable pageable) {
        return orderRepository.findByUserOrderByPlacedAtDesc(user, pageable);
    }

    public TacoOrder updateOrder(Long orderId, @Valid TacoOrder order) {
        order.setId(orderId);
        return orderRepository.save(order);
    }

    public Optional<TacoOrder> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public void deleteOrderById(Long orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {
            //do nothing about it
        }
    }


    // pre-authorize for ADMIN only in case this some other
    // service / controller will call this method
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

    public void initOrders() {
        AppUser apiUser = userRepository.findByUsername("api.user").orElseThrow(() ->
                new RuntimeException("api.user not found"));

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
        order1.setUser(apiUser);

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
        order2.setUser(apiUser);

        orderRepository.save(order2);

        // adding many orders for test users on start for test purposes
        AppUser testUser = userRepository.findByUsername("test.user").orElseThrow(() ->
                new RuntimeException("test.user not found"));

        for (int i = 1; i <= 100; i++) {
            // dummy order 1
            final int finalI = i;
            Taco taco = tacoRepository.findByName("Paging_Test_Taco_" + i).orElseThrow(() ->
                    new RuntimeException("Paging_Test_Taco_" + finalI + " not found"));

            TacoOrder tacoOrder = new TacoOrder();
            tacoOrder.setDeliveryCity("Some Dummy City");
            tacoOrder.setDeliveryName("Some Dummy Name");
            tacoOrder.setDeliveryState("Some Dummy State");
            tacoOrder.setDeliveryStreet("Some Dummy Street");
            tacoOrder.setDeliveryZip("Some Dummy Zip");
            tacoOrder.setCcCVV("123");
            tacoOrder.setCcNumber("374245455400126");
            tacoOrder.setCcExpiration("10/30");
            tacoOrder.setTacos(List.of(taco));
            tacoOrder.setUser(testUser);

            orderRepository.save(tacoOrder);
        }
    }
}
