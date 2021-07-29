package com.stapledon.oidc.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OpenIdConfiguration {
    @JsonProperty("issuer")
    public String issuer;

    @JsonProperty("jwks_uri")
    public String jwksUri;

    @JsonProperty("authorization_endpoint")
    public String authorizationEndpoint;

    @JsonProperty("token_endpoint")
    public String token_endpoint;

    @JsonProperty("userinfo_endpoint")
    public String userinfoEndpoint;

    @JsonProperty("end_session_endpoint")
    public String endSessionEndpoint;

    @JsonProperty("check_session_iframe")
    public String checkSessionIframe;

    @JsonProperty("frontchannel_logout_supported")
    public boolean frontchannelLogoutSupported;

    @JsonProperty("frontchannel_logout_session_supported")
    public boolean frontchannelLogoutSessionSupported;

    @JsonProperty("backchannel_logout_supported")
    public boolean backchannelLogoutSupported;

    @JsonProperty("backchannel_logout_session_supported")
    public boolean backchannelLogoutSessionSupported;

    @JsonProperty("scopes_supported")
    public List<String> scopesSupported;

    @JsonProperty("claims_supported")
    public List<String> claimsSupported;

    @JsonProperty("grant_types_supported")
    public List<String> grantTypesSupported;

    @JsonProperty("response_types_supported")
    public List<String> responseTypesSupported;

    @JsonProperty("response_modes_supported")
    public List<String> responseModesSupported;

    @JsonProperty("token_endpoint_auth_methods_supported")
    public List<String> tokenEndpointAuthMethodsSupported;

    @JsonProperty("id_token_signing_alg_values_supported")
    public List<String> idTokenSigningAlgValuesSupported;

    @JsonProperty("subject_types_supported")
    public List<String> subjectTypesSupported;

    @JsonProperty("code_challenge_methods_supported")
    public List<String> codeChallengeMethodsSupported;

    @JsonProperty("request_parameter_supported")
    public boolean requestParameterSupported;

    @JsonProperty("keep_alive_endpoint")
    public String keepAliveEndpoint;
}
