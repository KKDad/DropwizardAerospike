package com.stapledon.oauth2;

import com.stapledon.oidc.models.JwksConfiguration;
import com.stapledon.oidc.models.JwksKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import io.jsonwebtoken.io.Decoders;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Resolve the key that signed a jwt token based on Keys read from the OIDC configuration
 */
public class SigningKeyResolver extends SigningKeyResolverAdapter {
    private final Map<String, Key> keyMap;

    public SigningKeyResolver(JwksConfiguration jwksConfiguration) {
        Map<String, Key> newKeys =
                jwksConfiguration.keys.stream()
                        .filter(jwkKey -> "sig".equals(jwkKey.usage))
                        .filter(jwkKey -> "RSA".equals(jwkKey.keyType))
                        .collect(Collectors.toMap(JwksKey::getKeyIdentifier, jwkKey -> {
                            BigInteger modulus = base64ToBigInteger(jwkKey.modulus);
                            BigInteger exponent = base64ToBigInteger(jwkKey.exponent);
                            var rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exponent);
                            try {
                                var keyFactory = KeyFactory.getInstance("RSA");
                                return keyFactory.generatePublic(rsaPublicKeySpec);
                            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                                throw new IllegalStateException("Failed to parse public key");
                            }
                        }));

        keyMap = Collections.unmodifiableMap(newKeys);
    }

    @Override
    public Key resolveSigningKey(JwsHeader jwsHeader, Claims claims) {
        return keyMap.getOrDefault(jwsHeader.getKeyId(), null);
    }

    private BigInteger base64ToBigInteger(String value) {
        return new BigInteger(1, Decoders.BASE64URL.decode(value));
    }
}
