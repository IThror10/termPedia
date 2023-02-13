package com.TermPedia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    private final JwtAuthFilter filter;

    public SecurityConfig(JwtAuthFilter filter) {
        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        return http
                .exceptionHandling()
                .authenticationEntryPoint((req, res, authExp) -> res.setStatus(HttpStatus.UNAUTHORIZED.value()))
//                .accessDeniedHandler(handler)
                .and()
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/user").permitAll()
                        .requestMatchers("/api/v1/user/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/term/**").hasAuthority("VISITOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/**").authenticated()
                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin().disable()
//                .httpBasic().disable()
                .httpBasic(Customizer.withDefaults())
                .build();
    }

}
