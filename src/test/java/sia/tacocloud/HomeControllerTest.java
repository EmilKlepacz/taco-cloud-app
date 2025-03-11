package sia.tacocloud;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import sia.tacocloud.repository.IngredientRepository;
import sia.tacocloud.service.IngredientService;
import sia.tacocloud.service.RoleService;
import sia.tacocloud.service.UserService;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(sia.tacocloud.controller.HomeController.class) //
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mock missing beans to make test run without throwing NoSuchBeanDefinitionException
    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private IngredientService ingredientService;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

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