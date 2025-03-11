package sia.tacocloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sia.tacocloud.model.AppUser;
import sia.tacocloud.model.Role;
import sia.tacocloud.repository.RoleRepository;
import sia.tacocloud.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<AppUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void initUsers() {
        Optional<AppUser> adminUser = findByUsername("admin");
        if (adminUser.isEmpty()) {
            Role roleAdmin = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("No ADMIN role found"));

            AppUser admin = new AppUser(
                    "admin",
                    passwordEncoder.encode("test"),
                    "Admin User",
                    null,
                    null,
                    null,
                    null,
                    null,
                    Set.of(roleAdmin)
            );
            userRepository.save(admin);
        }
    }
}
