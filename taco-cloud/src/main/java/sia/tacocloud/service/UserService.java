package sia.tacocloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sia.tacocloud.model.AppUser;
import sia.tacocloud.model.Role;
import sia.tacocloud.repository.RoleRepository;
import sia.tacocloud.repository.UserRepository;

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
        if (findByUsername("admin").isEmpty()) {
            Role roleAdmin = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("No ADMIN role found"));

            AppUser adminUser = new AppUser(
                    null,
                    "admin",
                    passwordEncoder.encode("test"),
                    "Admin User",
                    "Dummny Street",
                    "Dummy City",
                    "Dummy State",
                    "Dummy Zip",
                    "+48 123 456 789",
                    Set.of(roleAdmin)
            );
            userRepository.save(adminUser);
        }

        if (findByUsername("test.user").isEmpty()) {
            AppUser testUser = new AppUser(
                    null,
                    "test.user",
                    passwordEncoder.encode("test"),
                    "Test User",
                    "Dummny Street",
                    "Dummy City",
                    "Dummy State",
                    "Dummy Zip",
                    "+48 123 456 789",
                    null
            );
            userRepository.save(testUser);
        }

        // for actions made by 'application'
        if (findByUsername("api.user").isEmpty()) {
            AppUser testUser = new AppUser(
                    null,
                    "api.user",
                    passwordEncoder.encode("test"),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            userRepository.save(testUser);
        }
    }
}
