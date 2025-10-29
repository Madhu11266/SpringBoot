package com.example.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.Session;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@Configuration
@EnableWebSecurity
public class DefaultFormSecurityConfig {




    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Bean
    public <S extends Session> SecurityFilterChain securityFilterChain(HttpSecurity http,FindByIndexNameSessionRepository<S> sessionRepository) throws Exception {

        SessionRegistry sessionRegistry = new SpringSessionBackedSessionRegistry<>(sessionRepository);
        http.authorizeHttpRequests(
                    auth->auth
                            .requestMatchers("/auth/register", "/h2-console/**","/auth/test2").permitAll()
                            .requestMatchers( "auth/test").hasRole("ADMIN")
                            .anyRequest().authenticated())
                 .csrf(csrf->csrf.ignoringRequestMatchers("/h2-console/**").disable())

                 .headers(headers -> headers
                         .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                 )
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).maximumSessions(1).
                                maxSessionsPreventsLogin(true).sessionRegistry(sessionRegistry))
         .formLogin(Customizer.withDefaults());
         return http.build();
    }
}
