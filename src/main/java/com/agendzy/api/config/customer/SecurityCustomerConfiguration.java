package com.agendzy.api.config.customer;

import com.agendzy.api.core.gateway.common.FindOneGateway;
import com.agendzy.api.core.gateway.customer.ExtractCustomerAuthTokenGateway;
import com.agendzy.api.core.usecase.common.boundary.input.query.WhereEmail;
import com.agendzy.api.core.usecase.customer.interactor.auth.AuthCustomerFilterUseCase;
import com.agendzy.api.entrypoint.http.security.customer.JwtCustomerAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
@Order(2)
@RequiredArgsConstructor
public class SecurityCustomerConfiguration {

    private final FindOneGateway<com.agendzy.api.core.domain.common.User, WhereEmail> findUserByEmail;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain customerFilterChain(HttpSecurity http,
                                                   JwtCustomerAuthenticationFilter jwtCustomerAuthenticationFilter) throws Exception {
        return http
            .securityMatcher("/v1/customers/**")
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsCustomerConfiguration()))
            .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationCustomerProvider())
            .addFilterBefore(jwtCustomerAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(request -> request
                .requestMatchers(HttpMethod.POST, "/v1/customers").permitAll()
                .requestMatchers(HttpMethod.POST, "/v1/customers/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .build();
    }

    @Bean
    public AuthenticationProvider authenticationCustomerProvider() {
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
    public CorsConfigurationSource corsCustomerConfiguration() {
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
    public JwtCustomerAuthenticationFilter jwtCustomerAuthenticationFilter(AuthCustomerFilterUseCase authCustomerFilterUseCase,
                                                                           ObjectMapper objectMapper,
                                                                           ExtractCustomerAuthTokenGateway extractAuthToken) {
        return new JwtCustomerAuthenticationFilter(authCustomerFilterUseCase, objectMapper, extractAuthToken);
    }

}
