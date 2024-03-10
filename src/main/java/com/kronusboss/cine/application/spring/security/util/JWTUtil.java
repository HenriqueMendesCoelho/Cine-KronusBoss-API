package com.kronusboss.cine.application.spring.security.util;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {

	private static final String AUDIENCE = "https://www.cine.kronusboss.com/api";

	@Value("${jwt.expiration}")
	public long expiration;

	private SecretKey getKey() {
		return Jwts.SIG.HS512.key().build();
	}

	public String generateToken(UUID userId, String username, String name, Set<String> roles) {
		return Jwts.builder()
				.subject(username)
				.expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getKey())
				.audience()
				.add(AUDIENCE)
				.and()
				.claim("id", userId)
				.claim("name", name)
				.claim("roles", roles.stream().map(s -> s.split("_")[1]).toList())
				.encodePayload(true)
				.compact();
	}

	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);

		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
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
			return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
		} catch (Exception e) {
			return null;
		}
	}

}
