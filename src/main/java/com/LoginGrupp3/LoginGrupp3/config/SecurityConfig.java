package com.LoginGrupp3.LoginGrupp3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.LoginGrupp3.LoginGrupp3.service.JpsUserDetailService;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    private final JpsUserDetailService jpsUserDetailService;

    public SecurityConfig(JpsUserDetailService jpsUserDetailService) {
        this.jpsUserDetailService = jpsUserDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests((request) -> request
                .requestMatchers("/orders").authenticated()
                .anyRequest().permitAll())

                .userDetailsService(jpsUserDetailService)

                .formLogin(Customizer.withDefaults())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"));


        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
