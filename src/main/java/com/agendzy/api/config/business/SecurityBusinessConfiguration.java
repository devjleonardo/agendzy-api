package com.agendzy.api.config.business;

import com.agendzy.api.core.gateway.business.ExtractCollaboratorAuthTokenGateway;
import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.usecase.business.interactor.auth.signin.AuthCollaboratorFilterUseCase;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereEmail;
import com.agendzy.api.entrypoint.http.security.business.JwtCollaboratorAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@Order(1)
@RequiredArgsConstructor
public class SecurityBusinessConfiguration {

    private final FindOneGateway<com.agendzy.api.core.domain.common.User, WhereEmail> findUserByEmail;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain collaboratorFilterChain(HttpSecurity http,
                                                       JwtCollaboratorAuthenticationFilter jwtFilter) throws Exception {
        return http
            .securityMatcher("/v1/businesses/**")
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsCollaboratorConfiguration()))
            .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationCollaboratorProvider())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(request -> request
                .requestMatchers(HttpMethod.OPTIONS, "**").permitAll()
                .requestMatchers(HttpMethod.GET, "/").permitAll()
                .requestMatchers(HttpMethod.GET, "/health-check").permitAll()
                .requestMatchers(HttpMethod.POST, "/v1/customers").permitAll()
                .requestMatchers(HttpMethod.POST, "/v1/businesses/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .build();
    }

    @Bean
    public AuthenticationProvider authenticationCollaboratorProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(username -> {
            var responseUser = findUserByEmail.execute(new WhereEmail(username));
            if (responseUser.isSuccess()) {
                var user = responseUser.getData();
                return new User(user.getEmail(), user.getPassword(), List.of());
            }
            throw new UsernameNotFoundException(String.format("User %s was not found in the database", username));
        });
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public CorsConfigurationSource corsCollaboratorConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        configuration.setExposedHeaders(List.of("Authorization", "Origin"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public JwtCollaboratorAuthenticationFilter jwtCollaboratorAuthenticationFilter(AuthCollaboratorFilterUseCase useCase,
                                                                                   ObjectMapper objectMapper,
                                                                                   ExtractCollaboratorAuthTokenGateway extractAuthToken) {
        return new JwtCollaboratorAuthenticationFilter(useCase, objectMapper, extractAuthToken);
    }

}
