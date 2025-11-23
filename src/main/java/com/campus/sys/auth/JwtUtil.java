package com.campus.sys.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
  @Value("${app.jwt.secret}")
  private String secret;
  @Value("${app.jwt.issuer}")
  private String issuer;
  @Value("${app.jwt.expireMinutes}")
  private long expireMinutes;

  public String generate(String subject, Map<String, Object> claims) {
    SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    Instant now = Instant.now();
    return Jwts.builder().subject(subject).issuer(issuer).claims(claims)
      .issuedAt(Date.from(now))
      .expiration(Date.from(now.plusSeconds(expireMinutes * 60)))
      .signWith(key)
      .compact();
  }

  public io.jsonwebtoken.Claims validate(String token) {
    SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
  }
}