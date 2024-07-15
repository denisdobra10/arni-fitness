package com.dodera.arni_fitness.security;

import com.dodera.arni_fitness.model.User;
import com.dodera.arni_fitness.repository.UserRepository;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    private final UserRepository userRepository;
    private final TokenService tokenService;

    public JwtAuthorizationFilter(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        logger.info("Entered Jwt Authorization Filter.......");
        if (request.getServletPath().equals("/api/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        var bearerToken = request.getHeader("Authorization");
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        bearerToken = bearerToken.substring(7);
        String email = tokenService.extractEmail(bearerToken);
        if (email == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "INVALID SESSION");
            return;
        }
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        final var authentication = tokenService.getAuthenticationForJwt(bearerToken, user.getAuthorities());
        if (authentication.isPresent()) {
            SecurityContextHolder.getContext().setAuthentication(authentication.get());
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "INVALID SESSION");
        }
    }
}
