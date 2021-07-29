package com.stapledon.oidc;

import com.stapledon.oidc.models.JwksConfiguration;
import com.stapledon.oidc.models.OpenIdConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class OidcHelperTest {

    @Test
    void loadOpenIdConfigurationTest() {
        // Act
        OpenIdConfiguration result = OidcHelper.loadOpenIdConfiguration("https://dfidqa.np.dayforcehcm.com/.well-known/openid-configuration");
        
        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("https://dfidqa.np.dayforcehcm.com", result.issuer);
        Assertions.assertEquals("https://dfidqa.np.dayforcehcm.com/.well-known/openid-configuration/jwks", result.jwksUri);
        Assertions.assertEquals("https://dfidqa.np.dayforcehcm.com/connect/authorize", result.authorizationEndpoint);
        Assertions.assertEquals("https://dfidqa.np.dayforcehcm.com/connect/token", result.token_endpoint);
        Assertions.assertEquals("https://dfidqa.np.dayforcehcm.com/connect/userinfo", result.userinfoEndpoint);
        Assertions.assertEquals("https://dfidqa.np.dayforcehcm.com/connect/checksession", result.checkSessionIframe);

        Assertions.assertTrue(result.backchannelLogoutSessionSupported);
        Assertions.assertTrue(result.backchannelLogoutSupported);
        Assertions.assertTrue(result.frontchannelLogoutSessionSupported);
        Assertions.assertTrue(result.frontchannelLogoutSupported);
        Assertions.assertTrue(result.requestParameterSupported);
    }

    @Test
    void loadJwksConfigurationTest() {
        // Act
        JwksConfiguration result = OidcHelper.loadJwksConfiguration("https://dfidqa.np.dayforcehcm.com/.well-known/openid-configuration/jwks");

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.keys.isEmpty());
        Assertions.assertEquals("RSA", result.keys.get(0).keyType);
        Assertions.assertEquals("sig", result.keys.get(0).usage);
        Assertions.assertEquals("C30516021FBFCC8467C3AEA70FD00D41ACBF7077", result.keys.get(0).keyIdentifier);
        Assertions.assertEquals("wwUWAh-_zIRnw66nD9ANQay_cHc", result.keys.get(0).x509CertificateThumbprint);
        Assertions.assertEquals("AQAB", result.keys.get(0).exponent);
    }
    
}

