package sia.tacocloud;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    //@ModelAttribute("tacoOrder"):
    // This method ensures that a TacoOrder object is added
    // to the model before rendering the form.
    // If tacoOrder already exists in the session
    // (because of @SessionAttributes), it will be used,
    // otherwise,
    // a new TacoOrder is created and added to the session and model.
    @ModelAttribute(name = "tacoOrder")
    public TacoOrder tacoOrder() {
        return new TacoOrder();  // Initialize TacoOrder and add it to the model
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        log.info("Order submitted: {}", order);
        sessionStatus.setComplete();

        return "redirect:/";
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }
}
