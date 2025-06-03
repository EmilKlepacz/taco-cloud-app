package sia.tacocloud.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sia.tacocloud.dto.enums.IngredientType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO {

    private String id;
    private String name;
    private IngredientType type;
}
