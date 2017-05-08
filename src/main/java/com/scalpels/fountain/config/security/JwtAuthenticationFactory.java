package com.scalpels.fountain.config.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtAuthenticationFactory {

	public static String generateJwtAuthentication(JwtSettings settings, String username,Collection<? extends GrantedAuthority> grantedAuthorities) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("scopes", grantedAuthorities.stream().map(s -> s.toString()).collect(Collectors.toList()));
		LocalDateTime currentTime = LocalDateTime.now();
		Date expiredAt = Date.from(currentTime.plusMinutes(settings.getExpirationTime()).atZone(ZoneId.systemDefault()).toInstant());
		String jwt = Jwts.builder().setClaims(claims).setIssuer(settings.getIssuer())
				.setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant())).setExpiration(expiredAt)
				.signWith(SignatureAlgorithm.HS512, settings.getSecretKey()).compact();

		return settings.getPrefix() + " " + jwt;
	}

	public static Authentication getJwtAuthentication(JwtSettings settings,String token) {
		if (token != null) {
			Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(settings.getSecretKey())
					.parseClaimsJws(token.replace(settings.getPrefix(), ""));
			// get the user
			String subject = jwsClaims.getBody().getSubject();
			@SuppressWarnings("unchecked")
			List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
			List<GrantedAuthority> authorities = scopes.stream().map(authority -> new SimpleGrantedAuthority(authority))
					.collect(Collectors.toList());

			return new UsernamePasswordAuthenticationToken(subject, token, authorities);
		}
		return null;
	}

}
