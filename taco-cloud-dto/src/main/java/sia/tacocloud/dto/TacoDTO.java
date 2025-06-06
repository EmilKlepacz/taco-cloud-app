package sia.tacocloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TacoDTO {

    private Long id;

    private Date createdAt;

    private String name;

    private List<IngredientDTO> ingredients;
}
