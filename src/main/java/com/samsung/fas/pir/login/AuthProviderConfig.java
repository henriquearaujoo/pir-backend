package com.samsung.fas.pir.login;

import com.samsung.fas.pir.login.providers.BAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@ComponentScan("com.samsung.fas.pir")
public class AuthProviderConfig extends WebSecurityConfigurerAdapter {
	private 	UserDetailsService 		userDetailsService;
	private		UnauthorizedHandler		handler;
	private		BAuthProvider			authprovider;

	@Autowired
	public AuthProviderConfig(UnauthorizedHandler handler, BAuthProvider authprovider, UserDetailsService userDetailsService) {
		this.handler				= handler;
		this.authprovider			= authprovider;
		this.userDetailsService		= userDetailsService;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().anyRequest().permitAll()
		
//		// Permissions for users endpoint (Create, Read, Update, Delete)
//		.antMatchers(HttpMethod.POST, "/users/**").hasAuthority("POST_USER")
//		.antMatchers(HttpMethod.GET, "/users/**").hasAuthority("GET_USER")
//		.antMatchers(HttpMethod.PUT, "/users/**").hasAuthority("PUT_USER")
//		
//		// Permissions for states endpoint (Create, Read, Update, Delete)
//		.antMatchers(HttpMethod.GET, "/states/**").hasAuthority("GET_STATE")
//		
//		// Permissions for rules endpoint (Create, Read, Update, Delete)
//		.antMatchers(HttpMethod.POST, "/rules/**").hasAuthority("POST_RULE")
//		.antMatchers(HttpMethod.GET, "/rules/**").hasAuthority("GET_RULE")
//		.antMatchers(HttpMethod.PUT, "/rules/**").hasAuthority("PUT_RULE")
//		.antMatchers(HttpMethod.DELETE, "/rules/**").hasAuthority("DELETE_RULE")
//		
//		// Permissions for profiles endpoint (Create, Read, Update, Delete)
//		.antMatchers(HttpMethod.POST, "/profiles/**").hasAuthority("POST_PROFILE")
//		.antMatchers(HttpMethod.GET, "/profiles/**").hasAuthority("GET_PROFILE")
//		.antMatchers(HttpMethod.PUT, "/profiles/**").hasAuthority("PUT_PROFILE")
//		
//		// Permissions for pages endpoint (Create, Read, Update, Delete)
//		.antMatchers(HttpMethod.GET, "/pages/**").hasAuthority("GET_PAGE")
//		.antMatchers(HttpMethod.POST, "/login/").permitAll()
//		.anyRequest().permitAll();
//		.and().authorizeRequests()
//		.antMatchers("/test/**").hasAuthority("POST_TEST").anyRequest().authenticated()
//		.and()
//		.addFilterBefore(new JWTAuthenticationFilter("/login/", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
//		.addFilterBefore(new JWTAuthorizationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
//		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		
		.and()
		.httpBasic()
		.authenticationEntryPoint(handler);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

}
