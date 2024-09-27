package sia.tacocloud;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class Taco {

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 chars long.")
    private String name;

    @NotNull
    @Size(min = 1, message = "You need to select at least 1 ingredient.")
    private List<Ingredient> ingredients;
}
