package com.samsung.fas.pir.configuration.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.samsung.fas.pir.configuration.properties.JWTProperties;
import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.configuration.security.rest.dto.AccountDTO;
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
	private 	JWTProperties	properties;

	@Autowired
	public JWToken(JWTProperties properties) {
		setProperties(properties);
	}

	@SuppressWarnings("unchecked")
	public String generateToken(Account account, Device device) {
		AccountDTO	 data		= new AccountDTO(account);
		JwtBuilder	builder 	= Jwts.builder();

		// Token generation
		builder.setClaims(new ObjectMapper().convertValue(data, Map.class));
		builder.setAudience(device.isMobile() || device.isTablet()? EAudience.MOBILE.getValue() : EAudience.WEB.getValue());
		builder.setIssuer(getProperties().getAppName());
		builder.setIssuedAt(new Date());
		builder.setExpiration(new Date(new Date().getTime() + (device.isTablet()? getProperties().getMobileExpiresIn() * 1000 * 3600 * 24 : getProperties().getExpiresIn() * 1000 * 3600 * 24)));
		builder.signWith(SignatureAlgorithm.HS512, getProperties().getSecret());
		return builder.compact();
	}

	public String getToken(HttpServletRequest request) {
		if (request.getHeader(getProperties().getHeader()) != null && request.getHeader(getProperties().getHeader()).startsWith(getProperties().getHeaderPrefix())) {
			return request.getHeader(getProperties().getHeader()).substring(getProperties().getHeaderPrefix().length());
		}
		return null;
	}

	public AccountDTO getAccount(String token) {
		try {
			return new Gson().fromJson(new Gson().toJson(Objects.requireNonNull(getClaims(token)).get("acc")), AccountDTO.class);
		} catch (Exception e) {
			return null;
		}
	}

	public Double getLatitude(String token) {
		try {
			return (Double) Objects.requireNonNull(getClaims(token)).get("lat");
		} catch (Exception e) {
			return null;
		}
	}

	public Double getLongitude(String token) {
		try {
			return (Double) Objects.requireNonNull(getClaims(token)).get("lng");
		} catch (Exception e) {
			return null;
		}
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		Account 		account 	= (Account) userDetails;
		Date 			created 	= issuedAt(token);
		Date			expiration	= expiresAt(token);
		Date			now			= new Date();

		return (account.getUser() != null && now.before(expiration) && now.after(created)) || (account.getUser() != null && account.getUser().getPerson() != null && account.getUser().getPerson().getAgent() != null);
	}

	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(getProperties().getSecret()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}

	private Date expiresAt(String token) {
		return Objects.requireNonNull(getClaims(token)).getExpiration();
	}

	private Date issuedAt(String token) {
		return Objects.requireNonNull(getClaims(token)).getIssuedAt();
	}
}