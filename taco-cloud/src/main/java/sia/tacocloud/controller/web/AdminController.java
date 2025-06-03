package sia.tacocloud.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sia.tacocloud.service.OrderService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final OrderService orderService;

    @Autowired
    public AdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String showAdminForm() {
        return "admin";
    }

    @PostMapping("/action")
    public String deleteAllOrders(RedirectAttributes redirectAttributes) {
        orderService.deleteAllOrders();
        redirectAttributes.addFlashAttribute("message", "You have successfully deleted all orders");
        return "redirect:/admin";
    }
}
