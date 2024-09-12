package com.harmlessprince.todomanagerapplication.filters;

import com.harmlessprince.todomanagerapplication.config.CustomUserDetailService;
import com.harmlessprince.todomanagerapplication.config.JwtService;
import com.harmlessprince.todomanagerapplication.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtService jwtService;

    private final CustomUserDetailService userDetailService;

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailService userDetailService) {
        this.jwtService = jwtService;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            final String authenticationHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;
            if (authenticationHeader == null || !authenticationHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
            } else {
                jwt = authenticationHeader.substring(7);
                //extract user email from jwt

                userEmail = jwtService.extractUsername(jwt);
                if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    User userDetails = userDetailService.loadUserByUsername(userEmail);
                    Collection<? extends GrantedAuthority> userAuthorities = userDetailService.getAuthorities(userDetails.getRole());
                    if (jwtService.validateJwtToken(jwt,  userDetails)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userAuthorities);
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }

                }
                filterChain.doFilter(request, response);
            }
        } catch (Exception exception) {
            logger.error("Cannot set user authentication", exception);
            filterChain.doFilter(request, response);
        }

//        filterChain.doFilter(request, response);
    }
}
