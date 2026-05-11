package com.fundbridge.startup.config;
import com.fundbridge.startup.security.HeaderAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final HeaderAuthFilter headerAuthFilter;

    public SecurityConfig(HeaderAuthFilter headerAuthFilter) {
        this.headerAuthFilter = headerAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/startups/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/documents/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/startups").hasRole("STARTUP")
                        .requestMatchers(HttpMethod.POST, "/api/documents").hasRole("STARTUP")
                        .requestMatchers(HttpMethod.PUT, "/api/startups/**").hasRole("STARTUP")
                        .requestMatchers(HttpMethod.PUT, "/api/documents/**").hasRole("STARTUP")
                        .requestMatchers(HttpMethod.DELETE, "/api/startups/**").hasRole("STARTUP")
                        .requestMatchers(HttpMethod.DELETE, "/api/documents/**").hasRole("STARTUP")


                        .anyRequest().authenticated()
                )
                .addFilterBefore(headerAuthFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
