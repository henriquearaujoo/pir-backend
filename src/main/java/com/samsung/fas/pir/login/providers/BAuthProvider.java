package com.samsung.fas.pir.login.providers;

import com.samsung.fas.pir.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BAuthProvider extends BaseAuthProvider {
	private UsersService uservice;

	@Autowired
	public BAuthProvider(UsersService service) {
		super();
		this.uservice = service;
	}


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
	    return new User(username, password, true, true, true, true, listRoles);
	}

}
