package com.agendzy.api.entrypoint.http.security.customer;

import com.agendzy.api.core.domain.customer.Customer;
import com.agendzy.api.core.gateway.customer.ExtractCustomerAuthTokenGateway;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
import com.agendzy.api.core.usecase.customer.boundary.output.data.auth.AuthCustomerTokenDataOutput;
import com.agendzy.api.core.usecase.customer.interactor.auth.AuthCustomerFilterUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JwtCustomerAuthenticationFilter extends OncePerRequestFilter {

    private final AuthCustomerFilterUseCase authCustomerFilterUseCase;
    private final ObjectMapper objectMapper;
    private final ExtractCustomerAuthTokenGateway extractAuthToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JwtCustomerAuthenticationFilter ATIVADO para: " + request.getRequestURI());

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");

        if (!StringUtils.hasText(authorization)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.replace("Bearer", "").replace("bearer", "").trim();

        if (!hasValidCustomerId(token)) {
            System.out.println("Token sem customerId, ignorando JwtCustomerAuthenticationFilter.");
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        OutputResponse<Customer> authResponse = authCustomerFilterUseCase.execute(token);

        if (authResponse.isError() || authResponse.getData() == null) {
            buildErrorResponse(response, authResponse);
            return;
        }

        handleAuthentication(request, authResponse.getData(), context);
        SecurityContextHolder.setContext(context);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().startsWith("/v1/customers");
    }

    private boolean hasValidCustomerId(String token) {
        try {
            AuthCustomerTokenDataOutput data = extractAuthToken.execute(token);
            return data.getTokenValid() && data.getCustomerId() != null;
        } catch (Exception e) {
            System.out.println("Erro ao validar token no JwtCustomerAuthenticationFilter: " + e.getMessage());
            return false;
        }
    }

    private void handleAuthentication(HttpServletRequest request, Customer customer, SecurityContext context) {
        var authority = new SimpleGrantedAuthority("ROLE_CUSTOMER");

        var authentication = new UsernamePasswordAuthenticationToken(
            getPrincipal(customer, List.of(authority)),
            null,
            List.of(authority)
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authentication);
    }

    private UserDetails getPrincipal(Customer customer, List<SimpleGrantedAuthority> authorities) {
        var user = customer.getUser();
        return new User(user.getEmail(), user.getPassword(), authorities);
    }

    private void buildErrorResponse(HttpServletResponse response, OutputResponse<?> authResponse) throws IOException {
        response.setStatus(401);
        response.setContentType("application/json; charset=UTF-8");

        var result = authResponse.getErrors().stream()
            .map(error -> Map.of("description", error.getDetail()))
            .toList();

        objectMapper.writeValue(response.getWriter(), Map.of("errors", result));
    }

}
