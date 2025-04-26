package sia.tacocloud;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import sia.tacocloud.controller.rest.TacoRestController;
import sia.tacocloud.dto.IngredientDTO;
import sia.tacocloud.dto.TacoDTO;
import sia.tacocloud.mapper.TacoMapper;
import sia.tacocloud.model.Ingredient;
import sia.tacocloud.model.Taco;
import sia.tacocloud.model.enums.IngredientType;
import sia.tacocloud.repository.IngredientRepository;
import sia.tacocloud.service.DataLoaderService;
import sia.tacocloud.service.TacoService;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TacoRestController.class)
public class TacoRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TacoService tacoService;

    @MockBean
    private TacoMapper tacoMapper;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private DataLoaderService dataLoaderService;

    private List<Taco> allTacos;
    private List<Ingredient> ingredients;
    private Map<IngredientType, sia.tacocloud.dto.enums.IngredientType> ingredientTypeEntityDtoMap;
    private Map<sia.tacocloud.dto.enums.IngredientType, IngredientType> ingredientTypeDtoEntityMap;

    @BeforeEach
    void setup() {
        prepareIngredients();
        prepareTacos();
        mockTacoMapper();
    }

    private void mockTacoMapper() {
        when(tacoMapper.toDto(any(Taco.class)))
                .thenAnswer(invocation -> {
                    Taco taco = invocation.getArgument(0);

                    TacoDTO tacoDTO = new TacoDTO();
                    tacoDTO.setId(taco.getId());
                    tacoDTO.setCreatedAt(taco.getCreatedAt());
                    tacoDTO.setName(taco.getName());

                    List<IngredientDTO> ingredientDTOs = taco.getIngredients().stream()
                            .map(ingredient -> new IngredientDTO(
                                    ingredient.getId(),
                                    ingredient.getName(),
                                    ingredientTypeEntityDtoMap.get(ingredient.getType())
                            ))
                            .collect(Collectors.toList());

                    tacoDTO.setIngredients(ingredientDTOs);
                    return tacoDTO;
                });

        when(tacoMapper.toEntity(any(TacoDTO.class)))
                .thenAnswer(invocation -> {
                    TacoDTO tacoDTO = invocation.getArgument(0);

                    Taco taco = new Taco();
                    taco.setId(tacoDTO.getId());
                    taco.setCreatedAt(tacoDTO.getCreatedAt());
                    taco.setName(tacoDTO.getName());

                    List<Ingredient> ingredients = tacoDTO.getIngredients().stream()
                            .map(ingredientDto -> new Ingredient(
                                    ingredientDto.getId(),
                                    ingredientDto.getName(),
                                    ingredientTypeDtoEntityMap.get(ingredientDto.getType())
                            ))
                            .collect(Collectors.toList());

                    taco.setIngredients(ingredients);
                    return taco;
                });
    }

    private void prepareTacos() {
        allTacos = IntStream.rangeClosed(1, 50)
                .mapToObj(i -> new Taco(
                        (long) i,
                        Date.from(Instant.now().plusSeconds(i)),
                        "Taco " + i,
                        new ArrayList<>(ingredients)
                ))
                .collect(Collectors.toList());
    }

    private void prepareIngredients() {
        ingredients = List.of(
                new Ingredient("IDG001", "Ingredient 1", IngredientType.CHEESE),
                new Ingredient("IDG002", "Ingredient 2", IngredientType.SAUCE)
        );

        ingredientTypeEntityDtoMap = Map.of(
                IngredientType.CHEESE, sia.tacocloud.dto.enums.IngredientType.CHEESE,
                IngredientType.SAUCE, sia.tacocloud.dto.enums.IngredientType.SAUCE);

        ingredientTypeDtoEntityMap = Map.of(
                sia.tacocloud.dto.enums.IngredientType.CHEESE, IngredientType.CHEESE,
                sia.tacocloud.dto.enums.IngredientType.SAUCE, IngredientType.SAUCE
        );
    }

    private void mockTacoServiceLastAddedTacos(int page, int size) {
        // lastAddedTacos
        PageRequest pageRequest = PageRequest.of(page, size);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + size), allTacos.size());
        List<Taco> paged = allTacos.subList(start, end);
        Page<Taco> tacoPage = new PageImpl<>(paged, pageRequest, allTacos.size());

        when(tacoService.lastAddedTacos(Mockito.eq(pageRequest)))
                .thenReturn(tacoPage);
    }

    private void mockTacoServiceGetTacoById(Long id) {
        Optional<Taco> tacoOpt = allTacos.stream()
                .filter(t -> Objects.equals(t.getId(), id))
                .findFirst();

        when(tacoService.getTacoById(id))
                .thenReturn(tacoOpt);
    }

    private void mockTacoServiceCreateTaco(Taco taco) {
        when(tacoService.createTaco(any(Taco.class)))
                .thenReturn(taco);
    }

    @Test
    @WithMockUser
        // simulate a logged-in user during this test and prevents 'Status expected:<200> but was:<302>'
    void recentTacos_shouldReturnPagedTacos() throws Exception {
        mockTacoServiceLastAddedTacos(0, 12);

        // Mock HTTP request
        mvc.perform(get("/api/tacos")
                        .param("recent", "")
                        .param("page", "0")
                        .param("size", "12")
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.content.length()").value(12))
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].name").value("Taco 1"))
                .andExpect(jsonPath("$.content[0].ingredients.length()").value(2))
                .andExpect(jsonPath("$.content[0].ingredients[0].id").value("IDG001"))
                .andExpect(jsonPath("$.content[0].ingredients[0].name").value("Ingredient 1"))
                .andExpect(jsonPath("$.content[0].ingredients[0].type").value("CHEESE"))
                .andExpect(jsonPath("$.content[0].ingredients[1].id").value("IDG002"))
                .andExpect(jsonPath("$.content[0].ingredients[1].name").value("Ingredient 2"))
                .andExpect(jsonPath("$.content[0].ingredients[1].type").value("SAUCE"))

                .andExpect(jsonPath("$.content[11].id").value("12"))
                .andExpect(jsonPath("$.content[11].name").value("Taco 12"))
                .andExpect(jsonPath("$.content[11].ingredients.length()").value(2))
                .andExpect(jsonPath("$.content[11].ingredients[0].id").value("IDG001"))
                .andExpect(jsonPath("$.content[11].ingredients[0].name").value("Ingredient 1"))
                .andExpect(jsonPath("$.content[11].ingredients[0].type").value("CHEESE"))
                .andExpect(jsonPath("$.content[11].ingredients[1].id").value("IDG002"))
                .andExpect(jsonPath("$.content[11].ingredients[1].name").value("Ingredient 2"))
                .andExpect(jsonPath("$.content[11].ingredients[1].type").value("SAUCE"));
    }

    @Test
    @WithMockUser
    void getTaco_shouldReturnTacoWithId() throws Exception {
        mockTacoServiceGetTacoById(1L);

        mvc.perform(get("/api/tacos/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getTaco_shouldReturn_shouldReturnNoFound_whenTacoNotFound() throws Exception {
        mockTacoServiceGetTacoById(99999L);

        mvc.perform(get("/api/tacos/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void createTaco_shouldReturnCreatedTaco() throws Exception {
        Taco taco = allTacos.get(0);

        TacoDTO tacoDTO = new TacoDTO();
        tacoDTO.setId(taco.getId());
        tacoDTO.setName(taco.getName());
        tacoDTO.setCreatedAt(taco.getCreatedAt());

        List<IngredientDTO> ingredientDTOs = taco.getIngredients().stream()
                .map(ingredient -> new IngredientDTO(
                        ingredient.getId(),
                        ingredient.getName(),
                        ingredientTypeEntityDtoMap.get(ingredient.getType())
                ))
                .collect(Collectors.toList());

        tacoDTO.setIngredients(ingredientDTOs);

        mockTacoServiceCreateTaco(taco);

        mvc.perform(post("/api/tacos")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tacoDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Taco 1"))
                .andExpect(jsonPath("$.ingredients.length()").value(2))
                .andExpect(jsonPath("$.ingredients[0].id").value("IDG001"))
                .andExpect(jsonPath("$.ingredients[0].name").value("Ingredient 1"))
                .andExpect(jsonPath("$.ingredients[0].type").value("CHEESE"));
    }
}
