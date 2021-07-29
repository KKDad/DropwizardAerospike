package com.stapledon.oauth2;

import com.stapledon.oidc.models.JwksConfiguration;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import java.util.Optional;

public class OAuth2Authenticator implements Authenticator<String, OAuth2Principal> {
    private final JwksConfiguration jwksConfiguration;

    public OAuth2Authenticator(JwksConfiguration jwksConfiguration) {
        this.jwksConfiguration = jwksConfiguration;
    }

    @Override
    public Optional<OAuth2Principal> authenticate(String credentials) throws AuthenticationException {
        return Optional.of(new OAuth2Principal(credentials, jwksConfiguration));
    }
}
