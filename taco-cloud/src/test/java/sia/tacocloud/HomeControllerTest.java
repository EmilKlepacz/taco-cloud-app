package sia.tacocloud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import sia.tacocloud.controller.web.HomeController;
import sia.tacocloud.repository.IngredientRepository;
import sia.tacocloud.repository.OrderRepository;
import sia.tacocloud.repository.TacoRepository;
import sia.tacocloud.service.*;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class) //
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mock missing beans to make test run without throwing NoSuchBeanDefinitionException
    // @WebMvcTest loads only web-related beans (@Controller) and not services - so other stuff needs to be explicitly declared.
    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private IngredientService ingredientService;

    @MockBean
    private TacoRepository tacoRepository;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private TacoService tacoService;

    @MockBean
    private OrderService orderService;

    @MockBean
    private DataLoaderService dataLoaderService;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content().string(
                        containsString("Welcome to...")));
    }
}