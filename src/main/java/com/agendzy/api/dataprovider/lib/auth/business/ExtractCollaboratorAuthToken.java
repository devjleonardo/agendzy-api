package com.agendzy.api.dataprovider.lib.auth.business;

import com.agendzy.api.core.domain.business.collaborator.CollaboratorRole;
import com.agendzy.api.core.gateway.business.ExtractCollaboratorAuthTokenGateway;
import com.agendzy.api.core.usecase.business.boundary.output.auth.AuthCollaboratorTokenDataOutput;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class ExtractCollaboratorAuthToken implements ExtractCollaboratorAuthTokenGateway {

    @Value("${security.jwt.secret}")
    private String secret;

    @Override
    public AuthCollaboratorTokenDataOutput execute(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return AuthCollaboratorTokenDataOutput.builder()
                    .email(claims.get("userEmail", String.class))
                    .collaboratorId(claims.get("collaboratorId", String.class))
                    .businessId(claims.get("businessId", String.class))
                    .role(getEnumClaimOrDefault(claims, "role", CollaboratorRole.class, null))
                    .tokenExpired(Boolean.FALSE)
                    .tokenValid(Boolean.TRUE)
                    .build();
        } catch (ExpiredJwtException exception) {
            return AuthCollaboratorTokenDataOutput.builder().tokenExpired(Boolean.TRUE).build();
        } catch (Exception e) {
            return AuthCollaboratorTokenDataOutput.builder().tokenValid(Boolean.FALSE).build();
        }
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private <T extends Enum<T>> T getEnumClaimOrDefault(Claims claims, String key, Class<T> enumClass, T defaultValue) {
        String value = claims.get(key, String.class);
        if (value != null) {
            try {
                return Enum.valueOf(enumClass, value.toUpperCase());
            } catch (IllegalArgumentException ignored) {
            }
        }
        return defaultValue;
    }

}
