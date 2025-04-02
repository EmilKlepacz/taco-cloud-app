package sia.tacocloud.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataLoaderService {
    private final IngredientService ingredientService;
    private final UserService userService;
    private final RoleService roleService;
    private final TacoService tacoService;
    private final OrderService orderService;

    public DataLoaderService(IngredientService ingredientService,
                             UserService userService,
                             RoleService roleService,
                             TacoService tacoService,
                             OrderService orderService) {
        this.ingredientService = ingredientService;
        this.userService = userService;
        this.roleService = roleService;
        this.tacoService = tacoService;
        this.orderService = orderService;
    }

    @Transactional
    public void initData() {
        ingredientService.initIngredients();
        roleService.initRoles();
        userService.initUsers();
        tacoService.initTacos();
        orderService.initOrders();
    }
}
