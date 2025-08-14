package com.andrew.todolist_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        // permite o acesso ao console do H2 sem autentificação
                        .requestMatchers("/h2-console/**").hasRole("ADMIN")
                        // Regras de ADMIN
                        .requestMatchers(HttpMethod.POST, "/tasks").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/tasks/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/tasks/{id}").hasRole("ADMIN")
                        // Regras de USER (e ADMIN)
                        .requestMatchers(HttpMethod.GET, "/tasks").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/tasks/{id}").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        // Permite acesso à documentação do Springdoc OpenAPI (Swagger UI e definição da API)
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**" ).hasAnyRole("User", "ADMIN")
                        // Qualquer outra requisição deve ser autenticada
                        .anyRequest().authenticated())

                .httpBasic(withDefaults())
                .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions
                        .disable()
                )
        );;

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("admin")
                        .roles("ADMIN")
                        .build(),
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("user")
                        .roles("USER")
                        .build()
        );
    }
}
