package sia.tacocloud.mapper;

import org.mapstruct.Mapper;
import sia.tacocloud.dto.AppUserDTO;
import sia.tacocloud.model.AppUser;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface AppUserMapper {

    AppUserDTO toDto(AppUser user);

    AppUser toEntity(AppUserDTO dto);
}