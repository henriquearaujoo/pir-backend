package com.samsung.fas.pir.login;

import com.samsung.fas.pir.login.auth.AuthEntryPoint;
import com.samsung.fas.pir.login.auth.JWToken;
import com.samsung.fas.pir.login.auth.TokenAuthenticationFilter;
import com.samsung.fas.pir.login.rest.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private 	AccountService 			service;
	private 	AuthEntryPoint 			entry;
	private 	JWToken 				token;

	@Autowired
	public SecurityConfig(AccountService service, AuthEntryPoint entry, JWToken token) {
		this.service	= service;
		this.entry		= entry;
		this.token		= token;
	}

	@Override 
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.anyRequest().permitAll()
		.and()
		.addFilterBefore(new TokenAuthenticationFilter(token, service), BasicAuthenticationFilter.class)
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.exceptionHandling().authenticationEntryPoint(entry)
		.and()
		.httpBasic()
		.authenticationEntryPoint(entry)
		.and().csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
		.antMatchers(HttpMethod.OPTIONS)
		.antMatchers(HttpMethod.POST, "/authentication/**")
		.antMatchers("/assets/**", "/webjars/**", "/api-docs/**")
		.antMatchers("/jsondoc/**", "/jsondoc-ui.html");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(passwordEncoder());
	}
}
