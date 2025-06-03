package sia.tacocloud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import sia.tacocloud.model.enums.IngredientType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ingredient {

    @Id
    private String id;

    private String name;

    private IngredientType type;

}
