package com.internship.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers
                // ✅ Fix for your issue
                .contentTypeOptions(contentType -> {})
                
                // 🔥 Recommended additional headers
                .frameOptions(frame -> frame.deny())
                .contentSecurityPolicy(csp -> 
                    csp.policyDirectives("default-src 'self'")
                )
            );

        return http.build();
    }
}