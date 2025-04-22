package sia.tacocloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sia.tacocloud.model.Role;
import sia.tacocloud.repository.RoleRepository;

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
            Role role = new Role(null, "ADMIN");
            roleRepository.save(role);
        }
    }
}
