package com.productsapp.security.filter;

import com.productsapp.jwt.JWTService;
import com.productsapp.security.SecurityConstants;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
public final class JWTRequestAuthenticationFilter extends OncePerRequestFilter {

    private UserDetailsManager userDetailsManager;

    private JWTService jwtService;

    public JWTRequestAuthenticationFilter(UserDetailsManager userDetailsManager, JWTService jwtService) {
        this.jwtService = jwtService;
        this.userDetailsManager = userDetailsManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String tokenHeader = request.getHeader(SecurityConstants.HEADER_STRING);
        if (tokenHeader == null || !tokenHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = tokenHeader.replace(SecurityConstants.TOKEN_PREFIX, "");

        String username;
        try {
            username = jwtService.getUsernameFromToken(jwtToken);
        } catch (JwtException ex) {
            throw new BadCredentialsException(ex.getMessage(), ex);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.getName().equals(username)) {
            if (!userDetailsManager.userExists(username)) {
                throw new UsernameNotFoundException(username);
            }

            UsernamePasswordAuthenticationToken authToken
                    = new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        chain.doFilter(request, response);
    }
}
