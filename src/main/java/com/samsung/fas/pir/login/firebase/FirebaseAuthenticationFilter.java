package com.samsung.fas.pir.login.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.samsung.fas.pir.exception.RESTRuntimeException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class FirebaseAuthenticationFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		final	String		token		= request.getHeader("X-Authorization-Firebase");
		if (token != null && !token.replaceAll("\\s+","").isEmpty()) {
			try {
				FirebaseToken	firebasetoken	= FirebaseAuth.getInstance().verifyIdTokenAsync(token).get();
				String			username		= firebasetoken.getUid();
				Authentication	authentication	= new FirebaseTokenAuthentication(username, firebasetoken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (InterruptedException | ExecutionException | BadCredentialsException e ) {
				throw new RESTRuntimeException(e.getMessage());
			}
		}
		chain.doFilter(request, response);
	}
}
