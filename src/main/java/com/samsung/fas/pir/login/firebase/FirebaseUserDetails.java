package com.samsung.fas.pir.login.firebase;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class FirebaseUserDetails implements UserDetails {
	@Setter
	@Getter
	private		String		username;

	@Setter
	@Getter
	private		String		password;

	@Setter
	@Getter
	private 	boolean		accountNonExpired;

	@Setter
	@Getter
	private 	boolean		accountNonLocked;

	@Setter
	@Getter
	private 	boolean		credentialsNonExpired;

	@Setter
	@Getter
	private		boolean 	enabled;

	@Setter
	@Getter
	private 	Collection<? extends GrantedAuthority> authorities;
}
