package com.example.pethealth.config;

import com.example.pethealth.filter.WebJwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity(debug = false)
@EnableWebMvc
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final WebJwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request -> {
                    request
                            .requestMatchers(
                                    "api/auth/login",
                                    "api/auth/register",
                                    "uploads/**",
                                    "api/home",
                                    "api/doctor/createAppointment",
                                    "api/serviceMedical",
                                    "api/typeQuestion/getAllTypeQuestion",
                                    "api/comment/getAllCommentQuestion/**"
                            ).permitAll()
                            .requestMatchers(HttpMethod.GET,"api/doctor/**").permitAll()
                            /*.requestMatchers(HttpMethod.POST,"posts").hasRole("ADMIN")*/
                            .anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}