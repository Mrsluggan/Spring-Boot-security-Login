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

        http

                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/icons/**").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/details/**").permitAll()
                        .requestMatchers("/orders").authenticated()
                        .requestMatchers("/createNewAccount").permitAll()
                        .requestMatchers("/image.png").permitAll()
                        .requestMatchers("/newUser").permitAll()
                        .requestMatchers("style.css").permitAll()
                        .requestMatchers("image.png").permitAll())

                .formLogin(form -> form
                        .defaultSuccessUrl("/"))


                        
                .userDetailsService(jpsUserDetailService)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
