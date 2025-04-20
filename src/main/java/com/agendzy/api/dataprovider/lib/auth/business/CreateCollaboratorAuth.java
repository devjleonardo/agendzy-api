package com.agendzy.api.dataprovider.lib.auth.business;

import com.agendzy.api.core.domain.business.Business;
import com.agendzy.api.core.domain.business.collaborator.Collaborator;
import com.agendzy.api.core.domain.common.User;
import com.agendzy.api.core.gateway.business.CreateCollaboratorAuthGateway;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class CreateCollaboratorAuth implements CreateCollaboratorAuthGateway {

    @Value("${security.jwt.secret}")
    private String secret;

    @Override
    public String execute(Collaborator collaborator, User user, Business business) {
        return Jwts.builder()
            .subject(user.getEmail())
            .issuedAt(Date.from(Instant.now()))
            .expiration(Date.from(Instant.now().plus(Duration.ofHours(8))))
            .claim("collaboratorId", collaborator.getId())
            .claim("userEmail", user.getEmail())
            .claim("userName", user.getFullName())
            .claim("businessId", business.getId())
            .claim("role", collaborator.getRole().name())
            .signWith(getSignKey(), Jwts.SIG.HS256)
            .compact();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
