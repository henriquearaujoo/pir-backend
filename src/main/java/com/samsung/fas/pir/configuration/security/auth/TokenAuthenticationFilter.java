package com.samsung.fas.pir.configuration.security.auth;

import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.configuration.security.rest.dto.AccountDTO;
import com.samsung.fas.pir.configuration.security.rest.service.AccountService;
import com.samsung.fas.pir.exception.ServiceException;
import com.samsung.fas.pir.persistence.dao.AgentDAO;
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
	private 	AgentDAO		agentDAO;

	public TokenAuthenticationFilter(JWToken token, AccountService service, AgentDAO agentDAO) {
		this.token 		= token;
		this.service 	= service;
		this.agentDAO	= agentDAO;
	}

	@Override
	public void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain chain) {
		try {
			String 		atoken 		= token.getToken(request);
			AccountDTO	account		= atoken != null? token.getAccount(atoken) : null;
			Double		latitude	= request.getHeader("Latitude") != null? Double.valueOf(request.getHeader("Latitude")) : null;
			Double		longitude	= request.getHeader("Longitude") != null? Double.valueOf(request.getHeader("Longitude")) : null;
//			String		fcmToken	= request.getHeader("fcm");
			UserDetails	details		= account != null? service.loadUserByUsername(account.getUsername()) : null;
			User		user		= account != null? ((Account) details).getUser() : null;

			if (details != null && token.validateToken(atoken, details)) {
				TokenAuthentication authentication = new TokenAuthentication(details, atoken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

			chain.doFilter(request, response);

			if (user != null && user.getPerson() != null && user.getPerson().getAgent() != null && latitude != null && longitude != null) {
//				user.setFcmToken(fcmToken);
				user.getPerson().getAgent().setLatitude(latitude);
				user.getPerson().getAgent().setLongitude(longitude);
				agentDAO.save(user.getPerson().getAgent());
			}

		} catch (IOException | ServletException | BadCredentialsException e) {
			throw new ServiceException(e.getMessage());
		}
	}
}