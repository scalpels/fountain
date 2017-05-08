package com.scalpels.fountain.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "scalpels.security.jwt")
public class JwtSettings {

	// default 7 day
	private long expirationTime = 10080;
//	private Date issuedAt = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
	private String issuer;
	private String secretKey;

	public static final String HEADER = "Authorization";
	
	private String prefix = "Bearer";

	public long getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(long expirationTime) {
		this.expirationTime = expirationTime;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}