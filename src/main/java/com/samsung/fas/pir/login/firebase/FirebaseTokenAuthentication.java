package com.samsung.fas.pir.login.firebase;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class FirebaseTokenAuthentication extends AbstractAuthenticationToken {
	@Getter
	private	final	Object	principal;

	@Getter
	private			Object	credentials;

	public FirebaseTokenAuthentication (Object principal, Object credentials) {
		super(null);
		super.setAuthenticated(false);
		this.principal		= principal;
		this.credentials	= credentials;
	}

	public FirebaseTokenAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		super.setAuthenticated(true);
		this.principal		= principal;
		this.credentials	= credentials;
	}

}
