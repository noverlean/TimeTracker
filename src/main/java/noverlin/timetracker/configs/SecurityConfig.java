package noverlin.timetracker.configs;

import noverlin.timetracker.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        final String ADMIN = "ADMIN";
        final String USER = "USER";

        http
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/projects/{id}").authenticated()
                        .requestMatchers("/projects").hasRole(ADMIN)
                        .requestMatchers("/projects/{id}/finish").hasRole(ADMIN)
                        .requestMatchers("/projects/{id}/resume").hasRole(ADMIN)
                        .requestMatchers("/users/self/projects").authenticated()
                        .requestMatchers("/projects/{id}/users").authenticated()
                        .requestMatchers("/projects/{id}/sessions/start").hasRole(USER)
                        .requestMatchers("/projects/{id}/sessions/finish").hasRole(USER)
                        .requestMatchers("/users").hasRole(ADMIN)
                        .requestMatchers("/users/email={email}").hasRole(ADMIN)
                        .requestMatchers("/users/email={email}").authenticated()
                        .requestMatchers("/projects/{projectId}/link/users/email={email}").hasRole(ADMIN)
                        .anyRequest().permitAll()
                );

        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
