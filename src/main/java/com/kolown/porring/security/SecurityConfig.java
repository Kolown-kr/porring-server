package com.kolown.porring.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kolown.porring.security.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final JwtService jwtService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpSessionOAuth2AuthorizationRequestRepository authorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorizeRequests -> {
            authorizeRequests.requestMatchers("/accounts/join", "/accounts/login", "/oauth2/**").permitAll();
            authorizeRequests.requestMatchers(HttpMethod.GET, "/boards").permitAll();
            authorizeRequests.anyRequest().authenticated();
        });

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.oauth2Login(
                oauth2Config -> {
                    oauth2Config.authorizationEndpoint(authorizationEndpointConfig -> {
                        authorizationEndpointConfig.authorizationRequestRepository(authorizationRequestRepository());
                    });
                    oauth2Config.successHandler(authenticationSuccessHandler());
                    oauth2Config.failureHandler(authenticationFailureHandler());
                }
        );

        http.logout(logoutConfig -> logoutConfig.logoutSuccessHandler(logoutSuccessHandler()));

        return http.build();
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK);
    }

    private AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String accessToken = jwtService.generateToken(authentication);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "OAuth2 로그인 성공");
            responseBody.put("accessToken", accessToken);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(responseBody));
        };
    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "OAuth2 로그인 실패");
            responseBody.put("error", exception.getMessage());

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(responseBody));
        };
    }

}
