package sia.auth.tacocloudauthserver.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataLoaderService {
    private final UserService userService;
    private final RoleService roleService;

    public DataLoaderService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Transactional
    public void initData() {
        roleService.initRoles();
        userService.initUsers();
    }
}
