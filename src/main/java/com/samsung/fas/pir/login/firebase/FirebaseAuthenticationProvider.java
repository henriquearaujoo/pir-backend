package com.samsung.fas.pir.login.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class FirebaseAuthenticationProvider implements AuthenticationProvider {
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (supports(authentication.getClass())) {
			try {
				final	FirebaseTokenAuthentication	firebasetoken	= (FirebaseTokenAuthentication) authentication;
				final 	UserRecord 					record			= Optional.ofNullable(FirebaseAuth.getInstance().getUserByEmailAsync(firebasetoken.getName()).get()).orElseThrow(() -> new UsernameNotFoundException("username.notfound"));
				final 	FirebaseUserDetails			details			= new FirebaseUserDetails();

				details.setUsername(record.getUid());
				details.setAuthorities(null);
				details.setAccountNonLocked(record.isEmailVerified());
				details.setEnabled(!record.isDisabled());
				details.setAccountNonExpired(true);
				details.setCredentialsNonExpired(true);
				details.setPassword(null);

				return new FirebaseTokenAuthentication(details, authentication.getCredentials(), details.getAuthorities());

			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (FirebaseTokenAuthentication.class.isAssignableFrom(authentication));
	}
}
