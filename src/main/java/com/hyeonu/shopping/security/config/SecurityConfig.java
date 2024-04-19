package com.hyeonu.shopping.security.config;

import com.hyeonu.shopping.dto.CustomUserDetails;
import com.hyeonu.shopping.security.filter.JwtAuthFilter;
import com.hyeonu.shopping.security.filter.LoginAuthenticationFilter;
import com.hyeonu.shopping.security.handler.ApiAuthenticationSuccessHandler;
import com.hyeonu.shopping.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(@Autowired final AuthenticationConfiguration authenticationConfiguration,
                          @Autowired final JwtTokenProvider jwtTokenProvider,
                          @Autowired final CustomUserDetailsService customUserDetailsService) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest
                                .requestMatchers("/manager/**").hasRole("manager")
                                .requestMatchers("/customer/**").hasRole("CUSTOMER")
                                .anyRequest().permitAll()
                )
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Filter 추가.
                .addFilterBefore(
                        new JwtAuthFilter(jwtTokenProvider, customUserDetailsService), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(
                        this.abstractAuthenticationProcessingFilter(authenticationManager, authenticationSuccessHandler()),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new ApiAuthenticationSuccessHandler();
    }


    public AbstractAuthenticationProcessingFilter abstractAuthenticationProcessingFilter(
        final AuthenticationManager authenticationManager,
        final AuthenticationSuccessHandler authenticationSuccessHandler) {
        return new LoginAuthenticationFilter(
                "/api/login",
                authenticationManager,
                authenticationSuccessHandler
        );
    }
}
