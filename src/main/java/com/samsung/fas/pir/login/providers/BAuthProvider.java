package com.samsung.fas.pir.login.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.samsung.fas.pir.login.BaseAuthProvider;

@Component
public class BAuthProvider extends BaseAuthProvider {
	@Override
	protected void checkUserCredentials(String username, String password) throws UsernameNotFoundException, BadCredentialsException {
		if (!username.equals("test")) {
	        throw new UsernameNotFoundException(username + " not found");
	    }
	 
	    if (!password.equals("password")) {
	        throw new BadCredentialsException(username + " password is not authenticated");
	    }
	}

	@Override
	protected User getUserDetails(String username, String password) {
		List<GrantedAuthority> listRoles = new ArrayList<>();
	    listRoles.add(new SimpleGrantedAuthority("GET_USER"));
	    
	    Collection<GrantedAuthority> authorities = listRoles;
	    boolean accountNonExpired = true;
	    boolean accountNonLocked = true;
	    boolean credentialsNonExpired = true;
	    boolean enabled = true;
	 
	    User appUser = new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	    return appUser;
	}

}
