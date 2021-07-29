package com.stapledon.oauth2;

import com.stapledon.oidc.models.JwksConfiguration;
import io.dropwizard.auth.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

public class OAuth2Principal implements Principal {
    private final Jws<Claims> claims;

    public OAuth2Principal(String accessToken, JwksConfiguration jwksConfiguration) throws AuthenticationException {
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKeyResolver(new SigningKeyResolver(jwksConfiguration))
                    .build()
                    .parseClaimsJws(accessToken);
        } catch (JwtException jwt) {
            throw new AuthenticationException(jwt.getLocalizedMessage());
        }
    }

    @Override
    public String getName() {
        return claims.getBody().get("name").toString();
    }

    public List<String> getRoles() {
        Object roles = claims.getBody().get("role");
        if (roles instanceof List)
            return (List<String>) claims.getBody().get("role");
        return Collections.emptyList();
    }

}
