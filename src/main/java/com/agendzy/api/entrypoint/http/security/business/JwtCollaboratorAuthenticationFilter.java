package com.agendzy.api.entrypoint.http.security.business;

import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.agendzy.api.core.usecase.business.interactor.auth.AuthCollaboratorFilterUseCase;
import com.agendzy.api.core.usecase.common.boundary.output.data.outputresponse.OutputResponse;
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
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtCollaboratorAuthenticationFilter extends OncePerRequestFilter {

    private final AuthCollaboratorFilterUseCase authCollaboratorFilterUseCase;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if ("OPTIONS".equals(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        String authorization = request.getHeader("Authorization");

        if (StringUtils.hasText(authorization)) {
            String formattedToken = authorization.replace("Bearer", "").replace("bearer", "").trim();

            OutputResponse<Collaborator> authResponse = authCollaboratorFilterUseCase.execute(formattedToken);

            if (authResponse.isError()) {
                buildErrorResponse(response, authResponse);
                return;
            }

            handleAuthentication(request, authResponse.getData(), context);
        }

        SecurityContextHolder.setContext(context);
        filterChain.doFilter(request, response);
    }

    private void handleAuthentication(HttpServletRequest request, Collaborator collaborator, SecurityContext context) {
        var authority = new SimpleGrantedAuthority(collaborator.getRole().name());

        var authentication = new UsernamePasswordAuthenticationToken(
            getPrincipal(collaborator, List.of(authority)),
            null,
            List.of(authority)
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authentication);
    }

    private UserDetails getPrincipal(Collaborator collaborator, List<SimpleGrantedAuthority> authorities) {
        var user = collaborator.getUser();
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
