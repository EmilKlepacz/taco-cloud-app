package sia.tacocloud.controller.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.dto.IngredientDTO;
import sia.tacocloud.mapper.IngredientMapper;
import sia.tacocloud.model.Ingredient;
import sia.tacocloud.repository.IngredientRepository;
import sia.tacocloud.service.IngredientService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/ingredients",
        produces = "application/json")
@CrossOrigin(origins = "http://tacocloud:8080")
public class IngredientController {
    private final IngredientMapper ingredientMapper;
    private final IngredientService ingredientService;

    public IngredientController(IngredientMapper ingredientMapper, IngredientService ingredientService) {
        this.ingredientMapper = ingredientMapper;
        this.ingredientService = ingredientService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<IngredientDTO> getIngredient(@PathVariable("id") String id) {
        Optional<Ingredient> ingredientOpt = ingredientService.getIngredientById(id);
        return ingredientOpt.map(ingredient -> new ResponseEntity<>(ingredientMapper.toDto(ingredient), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<Page<IngredientDTO>> getAllIngredients(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "12") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Ingredient> ingredients = ingredientService.getAllIngredients(pageRequest);

        Page<IngredientDTO> ingredientDTOS = ingredients.map(ingredientMapper::toDto);
        return ResponseEntity.ok(ingredientDTOS);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<IngredientDTO> createIngredient(@RequestBody IngredientDTO ingredientDTO) {
        Ingredient ingredient = ingredientMapper.toEntity(ingredientDTO);
        Ingredient createdIngredient = ingredientService.createIngredient(ingredient);

        IngredientDTO createdIngredientDTO = ingredientMapper.toDto(createdIngredient);
        return ResponseEntity.ok(createdIngredientDTO);
    }

    @DeleteMapping(path = "/{ingredientId}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable("ingredientId") String ingredientId) {
        ingredientService.deleteIngredientById(ingredientId);
        return ResponseEntity.noContent().build();
    }
}
