package com.samsung.fas.pir.configuration.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsung.fas.pir.configuration.properties.JWTProperties;
import com.samsung.fas.pir.configuration.security.rest.dto.AccountDTO;
import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.persistence.enums.EAudience;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Component
public class JWToken {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 	Date			date;

	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private 	JWTProperties	properties;

	@Autowired
	public JWToken(JWTProperties properties) {
		setDate(new Date());
		setProperties(properties);
	}

	public String generateToken(Account account) {
		AccountDTO	 data		= new AccountDTO(account);
		JwtBuilder	builder 	= Jwts.builder();

		// Token generation
		builder.setClaims(new ObjectMapper().convertValue(data, Map.class));
		builder.setAudience(EAudience.WEB.getValue());
		builder.setIssuer(getProperties().getAppName());
		builder.setIssuedAt(getDate());
		builder.setExpiration(new Date(getDate().getTime() + getProperties().getExpiresIn() * 1000 * 3600 * 24));
		builder.signWith(SignatureAlgorithm.HS512, getProperties().getSecret());
		return builder.compact();
	}

	public String getToken(HttpServletRequest request) {
		if (request.getHeader(getProperties().getHeader()) != null && request.getHeader(getProperties().getHeader()).startsWith(getProperties().getHeaderPrefix())) {
			return request.getHeader(getProperties().getHeader()).substring(getProperties().getHeaderPrefix().length());
		}
		return null;
	}

	public String getUserLogin(String token) {
		try {
			return Objects.requireNonNull(getAllClaimsFromToken(token)).get("ulg", String.class);
		} catch (Exception e) {
			return null;
		}
	}

	private Claims getAllClaimsFromToken(String token) {
		try {
			return Jwts.parser().setSigningKey(getProperties().getSecret()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}

	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		Account 	account 	= (Account) userDetails;
		String 		userlogin 	= getUserLogin(token);
		Date 		created 	= getIssuedAtDateFromToken(token);
		return (userlogin != null && userlogin.equals(userDetails.getUsername()));
	}

	private Date getIssuedAtDateFromToken(String token) {
		Claims claims = this.getAllClaimsFromToken(token);
		return claims != null ? claims.getIssuedAt() : null;
	}
}
