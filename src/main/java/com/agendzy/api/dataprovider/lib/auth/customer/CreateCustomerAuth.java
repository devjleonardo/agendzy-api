package com.agendzy.api.dataprovider.lib.auth.customer;

import com.agendzy.api.core.domain.common.User;
import com.agendzy.api.core.domain.customer.Customer;
import com.agendzy.api.core.gateway.customer.CreateCustomerAuthGateway;
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
public class CreateCustomerAuth implements CreateCustomerAuthGateway {

    @Value("${security.jwt.secret}")
    private String secret;

    @Override
    public String execute(Customer customer, User user) {
        return Jwts.builder()
            .subject(user.getEmail())
            .issuedAt(Date.from(Instant.now()))
            .expiration(Date.from(Instant.now().plus(Duration.ofHours(8))))
            .claim("customerId", customer.getId())
            .claim("userEmail", user.getEmail())
            .claim("userName", user.getFullName())
            .signWith(getSignKey(), Jwts.SIG.HS256)
            .compact();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
