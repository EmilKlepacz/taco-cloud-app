package sia.tacocloud.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import sia.tacocloud.dto.RoleDTO;
import sia.tacocloud.model.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDTO toDto(Role role);

    Role toEntity(RoleDTO dto);
}