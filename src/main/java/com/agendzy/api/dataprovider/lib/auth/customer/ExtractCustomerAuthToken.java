package com.agendzy.api.dataprovider.lib.auth.customer;

import com.agendzy.api.core.gateway.customer.ExtractCustomerAuthTokenGateway;
import com.agendzy.api.core.usecase.customer.boundary.output.data.auth.AuthCustomerTokenDataOutput;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class ExtractCustomerAuthToken implements ExtractCustomerAuthTokenGateway {

    @Value("${security.jwt.secret}")
    private String secret;

    @Override
    public AuthCustomerTokenDataOutput execute(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return AuthCustomerTokenDataOutput.builder()
                    .email(claims.get("userEmail", String.class))
                    .customerId(claims.get("customerId", String.class))
                    .tokenExpired(Boolean.FALSE)
                    .tokenValid(Boolean.TRUE)
                    .build();
        } catch (ExpiredJwtException exception) {
            return AuthCustomerTokenDataOutput.builder().tokenExpired(Boolean.TRUE).build();
        } catch (Exception e) {
            return AuthCustomerTokenDataOutput.builder().tokenValid(Boolean.FALSE).build();
        }
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
