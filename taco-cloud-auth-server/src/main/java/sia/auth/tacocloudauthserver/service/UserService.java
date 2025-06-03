package sia.auth.tacocloudauthserver.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sia.auth.tacocloudauthserver.model.Role;
import sia.auth.tacocloudauthserver.model.AppUser;
import sia.auth.tacocloudauthserver.repository.RoleRepository;
import sia.auth.tacocloudauthserver.repository.UserRepository;

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

            AppUser adminAppUser = new AppUser(
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
            userRepository.save(adminAppUser);
        }

        if (findByUsername("test.user").isEmpty()) {
            AppUser testAppUser = new AppUser(
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
            userRepository.save(testAppUser);
        }

        // for actions made by 'application'
        if (findByUsername("api.user").isEmpty()) {
            AppUser testAppUser = new AppUser(
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
            userRepository.save(testAppUser);
        }
    }
}

