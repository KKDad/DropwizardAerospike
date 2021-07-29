package com.stapledon.oauth2;

import io.dropwizard.auth.Authorizer;

public class OAuth2Authorizer implements Authorizer<OAuth2Principal> {

    @Override
    public boolean authorize(OAuth2Principal principal, String role) {
        return principal.getRoles().contains(role);
    }
}
