package com.kronusboss.cine.application.spring.security.util;

import java.security.Key;
import java.util.Date;
import java.util.Set;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	@Value("${jwt.expiration}")
	public long expiration;
	
	@Value("${jwt.secret}")
	public String secret;
	
	private Key getKey() {
		return new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS512.getJcaName());
	}
	
	public String generateToken(String username, String name, Set<String> roles) {
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis()+expiration))
				.signWith(getKey())
				.setAudience(username)
				.claim("name", name)
				.claim("roles", roles)
				.compact();
	}
	
	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		
		if(claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if(username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}
	
	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if(claims != null) {
			return claims.getSubject();
		}
		return null;
	}
	private Claims getClaims(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}
	
}
