package com.productsapp.security.filter;

import com.productsapp.jwt.JWTService;
import com.productsapp.jwt.JwtPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.productsapp.security.SecurityConstants.HEADER_STRING;
import static com.productsapp.security.SecurityConstants.TOKEN_PREFIX;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
public final class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JWTService jwtService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
        this.jwtService = jwtService;
        setAuthenticationManager(authenticationManager);
        setPostOnly(true);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request
            , HttpServletResponse response) throws AuthenticationException {
        if (!StringUtils.isEmpty(obtainPassword(request)) && !StringUtils.isEmpty(obtainUsername(request))) {
            return super.attemptAuthentication(request, response);
        }
        try {
            JwtPayload credentials = new ObjectMapper().readValue(request.getInputStream(), JwtPayload.class);
            UsernamePasswordAuthenticationToken token
                    = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
            return getAuthenticationManager().authenticate(token);
        } catch (IOException ex) {
            throw new HttpMessageNotReadableException(ex.getMessage(), ex);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse response,
                                            FilterChain chain, Authentication auth
    ) throws IOException, ServletException {
        response.addHeader(HEADER_STRING,
                TOKEN_PREFIX + jwtService.generateToken((UserDetails) auth.getPrincipal())
        );
    }
}
