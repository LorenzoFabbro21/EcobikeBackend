package microservice.authenticationservice.config;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.web.*;
import org.springframework.web.bind.annotation.*;

@Configuration
@EnableWebSecurity
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                    authorize
                            //.requestMatchers("/", "/auth/signup", "/auth/failureLogin", "/auth/login", "/auth/logout").permitAll()
                            .anyRequest().permitAll();
                })
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/google")
                        .defaultSuccessUrl("/auth/google", true)
                        .failureUrl("/auth/failureLogin")
                ).logout(logout -> logout.logoutUrl("/auth/logout/entry").logoutSuccessUrl("/api/authentication/logout"));
        return http.build();
    }
}
