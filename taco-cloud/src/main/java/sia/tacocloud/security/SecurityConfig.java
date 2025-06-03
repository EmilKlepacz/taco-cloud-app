package sia.tacocloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import sia.tacocloud.repository.UserRepository;

import java.util.List;

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
                // public
                .requestMatchers("/", "/login", "/h2-console/**").permitAll()

                // ROLE_USER -> role added for all logged users
                // OAUTH2_USER -> this is authority granted when logged with gitHub OAuth2
                .requestMatchers("/design", "/orders").hasAnyAuthority("ROLE_USER", "OAUTH2_USER") // requires authentication
                .requestMatchers("/admin").hasRole("ADMIN")

                //should be matched against OAuth 2 scopes in the access token given on the
                //request to those resources.
                .requestMatchers(HttpMethod.POST, "/api/ingredients").hasAuthority("SCOPE_writeIngredients")
                .requestMatchers(HttpMethod.DELETE, "/api/ingredients").hasAuthority("SCOPE_deleteIngredients")

                // needs access token
                .requestMatchers("/api/**").authenticated()

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

                .cors()
                .and()

                // enable resource server (JWT tokens)
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt())

                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // taco-cloud-client is running on 8081
        // I want it to access the resource server (running inside Docker)
        configuration.setAllowedOrigins(List.of("http://localhost:8081", "http://localhost:8080"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // Optional: If you want to allow cookies
        configuration.setMaxAge(3600L); // Optional: Cache pre-flight response for 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
