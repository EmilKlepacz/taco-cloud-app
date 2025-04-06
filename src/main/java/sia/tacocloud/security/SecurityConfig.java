package sia.tacocloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import sia.tacocloud.repository.UserRepository;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found."));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                // ROLE_USER -> role added for all logged users
                // OAUTH2_USER -> this is authority granted when logged with gitHub OAuth2
                .requestMatchers("/design", "/orders").hasAnyAuthority("ROLE_USER", "OAUTH2_USER") // requires authentication
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/", "/**").permitAll() // Everything else is public

                .and()
                .formLogin(form -> form
                        .loginPage("/login") // URL of your custom login page
                        .defaultSuccessUrl("/design") // go to design when logged in
                        .permitAll() // Allow everyone to see the login page
                )

                // OAuth2 Login Config for login with GitHub
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/design")
                )

                .logout(logout -> logout.logoutSuccessUrl("/"))

                // Make H2-Console non-secured; for debug purposes
                .csrf(csrf -> csrf.ignoringRequestMatchers(
                        "/h2-console/**",
                        //fixme: this /api is open for request only for development!
                        "/api/**")) // allow all /api for testing purposes only!!! -> to enable http requests

                // Allow pages to be loaded in frames from the same origin; needed for H2-Console
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin())
                )

                .build();
    }
}
