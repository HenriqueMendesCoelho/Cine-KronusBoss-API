package com.kronusboss.cine.application.spring.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Component
public class JWTUtil {

	private static final String AUDIENCE = "https://www.cine.kronusboss.com/api";
	private static final SecretKey key = Jwts.SIG.HS512.key().build();

	@Value("${jwt.secret}")
	public String secret;

	@Value("${jwt.expiration}")
	public long expiration;

	private Key getSigningKey() {
		byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(UUID userId, String username, String name, Set<String> roles) {
		return Jwts.builder()
				.subject(username)
				.expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSigningKey())
				.audience()
				.add(AUDIENCE)
				.and()
				.claim("id", userId)
				.claim("name", name)
				.claim("roles", roles.stream().map(s -> s.split("_")[1]).toList())
				.compact();
	}

	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);

		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			return username != null && expirationDate != null && now.before(expirationDate);
		}
		return false;
	}

	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
		} catch (Exception e) {
			return null;
		}
	}

}
