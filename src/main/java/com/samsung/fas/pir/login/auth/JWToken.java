package com.samsung.fas.pir.login.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsung.fas.pir.configuration.properties.JWTProperties;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.login.persistence.models.enums.EAudience;
import com.samsung.fas.pir.utils.IDCoder;
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

	public String generateToken(Account account, Device device) {
		JWTokenData		data		= new JWTokenData();
		JwtBuilder 		builder 	= Jwts.builder();

		// Account
		data.setUserlogin(account.getUsername());
		data.setUserid(IDCoder.encode(account.getUser().getUuid()));
		data.setUsername(account.getUser().getName());
		data.setPerfilID(IDCoder.encode(account.getProfile().getUuid()));
		data.setPerfil(account.getProfile().getTitle());

		// Token generation
		builder.setClaims(new ObjectMapper().convertValue(data, Map.class));
		builder.setAudience(generateAudience(device));
		builder.setIssuer(getProperties().getAppName());
		builder.setSubject("TEST");//((GrantedAuthority) account.getAuthorities().toArray()[0]).getAuthority());
		builder.setIssuedAt(getDate());
		builder.setExpiration(generateExpirationDate(device));
		builder.signWith(SignatureAlgorithm.HS512, getProperties().getSecret());
		return builder.compact();
	}

	private Date generateExpirationDate(Device device) {
		return new Date(date.getTime() + (device.isNormal() || device.isMobile() ? getProperties().getMobileExpiresIn() : getProperties().getExpiresIn()) * 1000 * 3600 * 24);
	}

	private String generateAudience(Device device) {
		return device.isNormal()? EAudience.WEB.toString() : device.isMobile()? EAudience.MOBILE.toString() : EAudience.UNKNOWN.toString();
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

	public Date getIssuedAtDateFromToken(String token) {
		Claims claims = this.getAllClaimsFromToken(token);
		return claims != null ? claims.getIssuedAt() : null;
	}

	public String refreshToken(String token, Device device) {
		Claims claims = this.getAllClaimsFromToken(token);
		if (claims != null) {
			claims.setIssuedAt(new Date());
			return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate(device)).signWith(SignatureAlgorithm.HS512, getProperties().getSecret()).compact();
		}
		return null;
	}

	public long getExpiredIn(Device device) {
		return device.isMobile() || device.isTablet() ? getProperties().getMobileExpiresIn() * 3600 * 24: getProperties().getExpiresIn() * 3600 * 24;
	}
}
