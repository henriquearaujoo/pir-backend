package com.samsung.fas.pir.configuration.security.auth;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.configuration.security.rest.dto.AccountDTO;
import com.samsung.fas.pir.configuration.security.rest.service.AccountService;
import com.samsung.fas.pir.exception.RESTException;
import com.samsung.fas.pir.persistence.dao.UserDAO;
import com.samsung.fas.pir.persistence.enums.EProfileType;
import com.samsung.fas.pir.persistence.models.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
	private 	JWToken 		token;
	private 	AccountService	service;
	private 	UserDAO			userDAO;

	public TokenAuthenticationFilter(JWToken token, AccountService service, UserDAO userDAO) {
		this.token 		= token;
		this.service 	= service;
		this.userDAO	= userDAO;
	}

	@Override
	public void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain) {
		try {
			String 		atoken 		= token.getToken(request);
			AccountDTO	account		= atoken != null? token.getAccount(atoken) : null;
			Double		latitude	= atoken != null? token.getLatitude(atoken) : null;
			Double		longitude	= atoken != null? token.getLongitude(atoken) : null;
			UserDetails	details		= account != null? service.loadUserByUsername(account.getUsername()) : null;
			User		user		= account != null? ((Account) details).getUser() : null;

			if (details != null && token.validateToken(atoken, details)) {
				TokenAuthentication authentication = new TokenAuthentication(details, atoken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

			chain.doFilter(request, response);

			if (user != null && user.getAccount().getProfile().getType().compareTo(EProfileType.AGENT) == 0) {
				user.setLatitude(latitude);
				user.setLongitude(longitude);
				userDAO.save(user);
			}

		} catch (IOException | ServletException | BadCredentialsException e) {
			throw new RESTException(e.getMessage());
		}
	}
}