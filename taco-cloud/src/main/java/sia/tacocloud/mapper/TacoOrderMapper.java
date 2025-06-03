package sia.tacocloud.mapper;

import org.mapstruct.Mapper;
import sia.tacocloud.dto.TacoDTO;
import sia.tacocloud.dto.TacoOrderDTO;
import sia.tacocloud.model.TacoOrder;

@Mapper(componentModel = "spring", uses = {AppUserMapper.class, TacoMapper.class})
public interface TacoOrderMapper {
    TacoOrderDTO toDto(TacoOrder order);

    TacoOrder toEntity(TacoOrderDTO dto);

}
