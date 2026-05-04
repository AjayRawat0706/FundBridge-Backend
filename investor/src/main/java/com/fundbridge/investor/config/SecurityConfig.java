package com.fundbridge.investor.config;
import com.fundbridge.investor.security.HeaderAuthFilter;
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

                        .requestMatchers(HttpMethod.GET, "/api/investor/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/investment/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/investors/preferences/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/investor").hasRole("INVESTOR")
                        .requestMatchers(HttpMethod.POST, "/api/investment").hasRole("INVESTOR")
                        .requestMatchers(HttpMethod.POST, "/api/investors/preferences").hasRole("INVESTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/investor/**").hasRole("INVESTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/investment/**").hasRole("INVESTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/investors/preferences/**").hasRole("INVESTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/investor/**").hasRole("INVESTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/investment/**").hasRole("INVESTOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/investors/preferences/**").hasRole("INVESTOR")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(headerAuthFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
