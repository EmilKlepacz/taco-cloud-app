package sia.tacocloud.mapper;

import org.mapstruct.Mapper;
import sia.tacocloud.dto.TacoDTO;
import sia.tacocloud.model.Taco;

@Mapper(componentModel = "spring", uses = {IngredientMapper.class})
public interface TacoMapper {
    TacoDTO toDto(Taco taco);

    Taco toEntity(TacoDTO dto);
}
