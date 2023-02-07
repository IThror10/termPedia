package com.TermPedia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
//    private final AuthenticationProvider authProvider;
    private final JwtAuthFilter filter;

    public SecurityConfig(/*AuthenticationProvider authProvider, */JwtAuthFilter filter) {
//        this.authProvider = authProvider;
        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        return http
//                .exceptionHandling()
//                .authenticationEntryPoint(controller)
//                .accessDeniedHandler(handler)
//                .and()
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/v1/user/login").permitAll()
                                .requestMatchers("/api/v1/user/register").permitAll()
                                .requestMatchers("/greeting").authenticated()
//                                .requestMatchers("/api/v1/term").authenticated()
                                .anyRequest().permitAll()
//                        .requestMatchers("/").permitAll()
//                        .anyRequest().authenticated()
//                        .requestMatchers(HttpMethod.POST, "/api/v1/**").authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
//                .authenticationProvider(authProvider)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin().disable()
//                .httpBasic().disable()
//                .httpBasic(Customizer.withDefaults())
                .build();
    }

}
