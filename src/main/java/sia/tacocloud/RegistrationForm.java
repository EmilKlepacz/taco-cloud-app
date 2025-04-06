package sia.tacocloud;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import sia.tacocloud.model.AppUser;
import sia.tacocloud.model.Role;

import java.util.Set;

@Data
public class RegistrationForm {

    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public AppUser toUser(PasswordEncoder passwordEncoder) {
        return new AppUser(
                username,
                passwordEncoder.encode(password),
                fullname,
                street,
                city,
                state,
                zip,
                phone,
                Set.of(new Role("USER")));
    }

}