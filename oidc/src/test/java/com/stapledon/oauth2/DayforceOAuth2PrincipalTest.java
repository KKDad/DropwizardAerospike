package com.stapledon.oauth2;

import com.stapledon.oidc.OidcHelper;
import com.stapledon.oidc.models.JwksConfiguration;
import io.dropwizard.auth.AuthenticationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DayforceOAuth2PrincipalTest {

    @Test
    void getName() {
        // Arrange
        try {
            JwksConfiguration jwksConfiguration = OidcHelper.loadJwksConfiguration("https://dfiddev.np.dayforcehcm.com/.well-known/openid-configuration/jwks");
            String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjA1Mjk5NTQ2NjlBNUFEQUM0ODM1QzNGOTI4QzZDNTNDMzY1RjEyNzYiLCJ0eXAiOiJhdCtqd3QiLCJ4NXQiOiJCU21WUm1tbHJheElOY1A1S01iRlBEWmZFblkifQ.eyJuYmYiOjE2Mjc0OTg4MzksImV4cCI6MTYyNzUwMjQzOSwiaXNzIjoiaHR0cHM6Ly9kZmlkZGV2Lm5wLmRheWZvcmNlaGNtLmNvbSIsImF1ZCI6WyJkZi5pZGVudGl0eS5hZG1pbmFwaSIsImh0dHBzOi8vZGZpZGRldi5ucC5kYXlmb3JjZWhjbS5jb20vcmVzb3VyY2VzIl0sImNsaWVudF9pZCI6IkRheWZvcmNlSWRlbnRpdHkuQWRtaW4iLCJzdWIiOiJNUEVaSXd2dVhnUlRqeU5rc1otVWN5RG15X2hpb1lPM281MXpSZzdFVjNvIiwiYXV0aF90aW1lIjoxNjI3NDk4ODM3LCJpZHAiOiJjZXJpZGlhbl9lbXBsb3llZSIsInJvbGUiOlsiQ2VyaWRpYW4uQ0FOLkFsbCIsIlJvbGUtUHJvZHVjdC1ERlJQRGV2ZWxvcGVyIiwiQ2VyaWRpYW4uTkEuQWxsIiwiQ2VyaWRpYW4uQ0FOLlZpcnR1YWwuQWxsIiwiQ2VyaWRpYW4uTkEuVmlydHVhbC5BbGwiLCJFWFNNLTExMTRCLU9XIiwiQ2VyaWRpYW4uZVByb21vcy5Vc2VycyIsIkNlcmlkaWFuLkNBTi5PbnRhcmlvLlZpcnR1YWwiLCJFWFNNLTEwOTE4LU9XIiwiUm9sZS1Qcm9kREYtREZRQSIsIkNlcmlkaWFuLlByb2R1Y3QuQWxsIiwiQ2VyaWRpYW4gQXp1cmUgQWRtaW4gQWNjb3VudCBPd25lcnMiLCJDZXJpZGlhbi5Qcm9kdWN0LkNBTiIsIlJvbGUtQ2VyaWRpYW4tRW1wbG95ZWVzQWN0aXZlIiwiQ2VyaWRpYW4uQ0FOLk9udGFyaW8uQWxsIiwiQ2VyaWRpYW4uR2xvYmFsLkFsbCIsIkNlcmlkaWFuIEF6dXJlIEFubm91bmNlbWVudHMiLCJDZXJpZGlhbi5TYWZhcmlCb29rc09ubGluZS5Vc2VycyIsIkNlcmlkaWFuLkNBTi5Ub3JvbnRvLkFsbCIsIlJvbGUtUHJvZERGLURGRGV2ZWxvcGVyIl0sImFwaV92ZXJzaW9uIjoiMS4wIiwibmFtZSI6IkdpbGJlcnQsIEFkcmlhbiIsImRmLmV4dGVybmFsX2xvZ291dHJlZGlyZWN0dXJsIjoiaHR0cHM6Ly9sb2dpbi5taWNyb3NvZnRvbmxpbmUuY29tLzI4OTMyMWUwLTlkYjYtNDY0NC1iMzcxLTk1NmU2MDU2ZDllYi9vYXV0aDIvdjIuMC9sb2dvdXQiLCJkZi5leHRlcm5hbF91c2VybmFtZSI6Ik1QRVpJd3Z1WGdSVGp5TmtzWi1VY3lEbXlfaGlvWU8zbzUxelJnN0VWM28iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJBZHJpYW4uR2lsYmVydEBjZXJpZGlhbi5jb20iLCJqdGkiOiJpeUNCTzhGZzJjcnNaRUpQWlRDcE9nIiwic2NvcGUiOlsib3BlbmlkIiwicHJvZmlsZSIsImRheWZvcmNlIiwiZGYuaWRlbnRpdHkuc3VwcG9ydCJdLCJhbXIiOlsiZXh0ZXJuYWwiXX0.Tax0gamYGitQzdilS1mnmGrnYxt3XkTGPIIRFp0JNxRxVrDOUv0s3GDP_5Q8LK7Se9qPkGKfUoQsSE74QNUGD7UbM2QN2h7f5PHYA2s5jF4kwctyD22F04ylD5APaRI4PJ2w_XY3fJ84092HLHWy4RQQuZo4WWBh43ZoH8d7hfuqWqTbZzZcWX7_l0fPpq8fCNH-bbr8147BFw9rt4Qex5fRPQdgInkM0860H_nBtc_JTZvjv-gMJsqnVj-3ZEgbEBYuyPQiMs3I80XbNGM9ekeBRfDeropbNDbPfqtvA6OvJ74SYt3WSJ9qQPTgl_LIdcFHCx7v3GAvUQAh0AGDcw";
            OAuth2Principal subject = new OAuth2Principal(token, jwksConfiguration);

            // Act
            String result = subject.getName();

            // Assert
            Assertions.assertEquals("", result);

        } catch (AuthenticationException ae) {
            Assertions.fail(ae.getLocalizedMessage());
        }




    }
}