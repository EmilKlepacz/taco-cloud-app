package sia.tacocloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sia.tacocloud.model.AppUser;
import sia.tacocloud.model.Role;
import sia.tacocloud.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // for testing !
    public void initUsers() {
        Optional<AppUser> adminUser = userRepository.findByUsername("admin");
        if (adminUser.isEmpty()) {
            //todo add role and save in repo
            AppUser admin = new AppUser(
                    "admin",
                    passwordEncoder.encode("test"),
                    "Admin User",
                    null,
                    null,
                    null,
                    null,
                    null,
                    Set.of(new Role("ADMIN"))
            );
        }
    }
}
