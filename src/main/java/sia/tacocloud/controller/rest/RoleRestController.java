package sia.tacocloud.controller.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.dto.RoleDTO;
import sia.tacocloud.mapper.RoleMapper;
import sia.tacocloud.model.Role;
import sia.tacocloud.repository.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/roles",
        produces = "application/json")
@CrossOrigin(origins = "http://tacocloud:8080")
public class RoleRestController {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleRestController(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @GetMapping
    public ResponseEntity<Page<RoleDTO>> getAllRoles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Role> roles = roleRepository.findAll(pageRequest);

        List<RoleDTO> roleDTOs = roles.stream()
                .map(role -> roleMapper.toDto(role))
                .collect(Collectors.toList());

        Page<RoleDTO> roleDTOPage = new PageImpl<>(roleDTOs, pageRequest, roleDTOs.size());
        return ResponseEntity.ok(roleDTOPage);
    }
}
