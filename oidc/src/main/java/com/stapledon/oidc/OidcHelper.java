package com.stapledon.oidc;

import com.stapledon.oidc.models.JwksConfiguration;
import com.stapledon.oidc.models.OpenIdConfiguration;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;

public class OidcHelper {

    private static final Logger LOG = LoggerFactory.getLogger(OidcHelper.class);


    private OidcHelper() {
        // Utility Class
    }

    public static OpenIdConfiguration loadOpenIdConfiguration(String location) {
        Client client = JerseyClientBuilder.createClient();
        var response = client.target(location)
                                  .request()
                                  .get();
        response.bufferEntity();
        return response.readEntity(OpenIdConfiguration.class);
    }

    public static JwksConfiguration loadJwksConfiguration(String location) {
        Client client = JerseyClientBuilder.createClient();
        var response = client.target(location)
                .request()
                .get();
        response.bufferEntity();
        return response.readEntity(JwksConfiguration.class);
    }

}