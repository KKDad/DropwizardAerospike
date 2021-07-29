package com.stapledon.oidc.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

// https://redthunder.blog/2017/06/08/jwts-jwks-kids-x5ts-oh-my/
// https://www.gnupg.org/documentation/manuals/gcrypt-devel/RSA-key-parameters.html
public class JwksKey {
    @JsonProperty("kty")
    public String keyType;

    @JsonProperty("use")
    public String usage;  // ‘sig’ for signing keys, ‘enc’ for encryption keys

    @JsonProperty("kid")
    public String keyIdentifier;

    @JsonProperty("x5t")
    public String x509CertificateThumbprint;

    @JsonProperty("e")
    public String exponent;

    @JsonProperty("n")
    public String modulus;

    @JsonProperty("x5c")
    public List<String> x509CertificateChain;

    @JsonProperty("alg")
    public String algorithm;

    public String getKeyIdentifier()
    {
        return keyIdentifier;
    }
}
