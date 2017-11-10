package com.samsung.fas.pir.login;

import com.samsung.fas.pir.dao.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserLoginDetailsService implements UserDetailsService {
	private UsersDAO udao;

	@Autowired
	public UserLoginDetailsService(UsersDAO dao) {
		this.udao = dao;
	}

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		com.samsung.fas.pir.models.entity.User user =  udao.findOneByLogin(s);

		if (user == null) {
			throw new UsernameNotFoundException(s);
		}
		// TODO: Get Authorities
		return new User(user.getLogin(), user.getPassword(), Collections.emptyList());
	}
}
