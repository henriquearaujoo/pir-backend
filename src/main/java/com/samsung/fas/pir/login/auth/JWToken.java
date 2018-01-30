package com.samsung.fas.pir.login.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.login.persistence.models.enums.EAudience;
import com.samsung.fas.pir.utils.IDCoder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Component
public class JWToken {
	private 	static	String					STARTS			= "PIRFAS=";
	private 			Date					date;

	@Value("${jwt.secret}")
	private				String					secret;

	@Value("${jwt.app.name}")
	private				String					appName;

	@Value("${jwt.expires_in}")
	private				long					expires;

	@Value("${jwt.mobile_expires_in}")
	private				long					mobileExpires;

	@Value("${jwt.header}")
	private				String					header;

	public JWToken() {
		date	= new Date();
	}

	// region Generate token
	public String generateToken(Account account, Device device) {
		JWTokenData		data		= new JWTokenData();
		JwtBuilder 		builder 	= Jwts.builder();

		// Account
		data.setUserlogin(account.getUsername());
		data.setUserid(IDCoder.encode(account.getUser().getGuid()));
		data.setUsername(account.getUser().getName());
		data.setPerfil(IDCoder.encode(account.getProfile().getGuid()));

		// Token generation
		builder.setClaims(new ObjectMapper().convertValue(data, Map.class));
		builder.setAudience(generateAudience(device));
		builder.setIssuer(appName);
		builder.setSubject("TEST");//((GrantedAuthority) account.getAuthorities().toArray()[0]).getAuthority());
		builder.setIssuedAt(date);
		builder.setExpiration(generateExpirationDate(device));
		builder.signWith(SignatureAlgorithm.HS512, secret);
		return builder.compact();
	}

	private Date generateExpirationDate(Device device) {
		return new Date(date.getTime() + (device.isNormal() || device.isMobile() ? mobileExpires : expires) * 1000 * 3600 * 24);
	}

	private String generateAudience(Device device) {
		return device.isNormal()? EAudience.WEB.toString() : device.isMobile()? EAudience.MOBILE.toString() : EAudience.UNKNOWN.toString();
	}
	// endregion

	public String getToken(HttpServletRequest request) {
		if (request.getHeader(header) != null && request.getHeader(header).startsWith(STARTS)) {
			return request.getHeader(header).substring(STARTS.length());
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
			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
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
			return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate(device)).signWith(SignatureAlgorithm.HS512, secret).compact();
		}
		return null;
	}

	public long getExpiredIn(Device device) {
		return device.isMobile() || device.isTablet() ? mobileExpires * 3600 * 24: expires * 3600 * 24;
	}
}
