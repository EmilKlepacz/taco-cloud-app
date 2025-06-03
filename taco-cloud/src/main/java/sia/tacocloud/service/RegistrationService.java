package sia.tacocloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sia.tacocloud.RegistrationForm;
import sia.tacocloud.repository.UserRepository;

@Service
public class RegistrationService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void processRegistration(RegistrationForm form) {
        userRepository.save(form.toUser(passwordEncoder));
    }
}
