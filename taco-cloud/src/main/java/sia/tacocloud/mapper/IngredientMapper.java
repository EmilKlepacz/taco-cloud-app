package sia.tacocloud.mapper;

import org.mapstruct.Mapper;
import sia.tacocloud.dto.IngredientDTO;
import sia.tacocloud.model.Ingredient;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    IngredientDTO toDto(Ingredient ingredient);

    Ingredient toEntity(IngredientDTO dto);
}
