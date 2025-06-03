package sia.auth.tacocloudauthserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sia.auth.tacocloudauthserver.model.Role;
import sia.auth.tacocloudauthserver.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    public void initRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.saveAll(List.of(
                    new Role(null, "ADMIN"),
                    new Role(null, "TEST")
            ));
        }
    }

    Page<Role> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }
}
