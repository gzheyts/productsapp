package com.productsapp.jwt;

import com.productsapp.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */

@Component
public class JWTService {

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(
                Jwts.parser().setSigningKey(SecurityConstants.SECRET)
                        .parseClaimsJws(token)
                        .getBody()
        );
    }
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setIssuedAt(DefaultClock.INSTANCE.now())
                .setSubject(userDetails.getUsername())
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }
}
