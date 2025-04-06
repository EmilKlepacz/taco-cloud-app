package sia.tacocloud.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import sia.tacocloud.model.AppUser;
import sia.tacocloud.model.TacoOrder;
import sia.tacocloud.service.OrderService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
@ConfigurationProperties(prefix = "taco.orders")
public class OrderController {
    private int pageSize; //page size for listing historical orders;

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder tacoOrder() {
        return new TacoOrder();  // Initialize TacoOrder and add it to the model
    }

    @ModelAttribute(name = "ordersHistory") //historical tacos ordered by user
    public List<TacoOrder> ordersHistory(@AuthenticationPrincipal AppUser user) {
        Pageable pageable = PageRequest.of(0, pageSize);
        return orderService.ordersForUser(user, pageable);
    }

    @GetMapping("/history")
    public String ordersForUser() {
        return "history";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order,
                               Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal AppUser user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

//        log.info("Order submitted: {}", order);
        order.setUser(user);

        orderService.processOrder(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }
}
