package sia.tacocloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import sia.tacocloud.model.AppUser;
import sia.tacocloud.repository.UserRepository;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            AppUser appUser = userRepo.findByUsername(username);
            if (appUser != null) return appUser;

            throw new UsernameNotFoundException("Username: " + username + " not found.");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .requestMatchers("/design", "/orders").hasRole("USER") // requires authentication
                .requestMatchers("/", "/**").permitAll() // Everything else is public
                .and()

                .formLogin(form -> form
                        .loginPage("/login") // URL of your custom login page
                        .defaultSuccessUrl("/design") // go to design when logged in
                        .permitAll() // Allow everyone to see the login page
                )

                .logout(logout -> logout.logoutSuccessUrl("/"))

                // Make H2-Console non-secured; for debug purposes
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))

                // Allow pages to be loaded in frames from the same origin; needed for H2-Console
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
                )

                .build();
    }
}
