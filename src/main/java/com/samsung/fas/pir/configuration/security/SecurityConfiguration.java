package com.samsung.fas.pir.configuration.security;

import com.samsung.fas.pir.configuration.security.auth.AuthEntryPoint;
import com.samsung.fas.pir.configuration.security.auth.JWToken;
import com.samsung.fas.pir.configuration.security.auth.TokenAuthenticationFilter;
import com.samsung.fas.pir.configuration.security.rest.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	private	final 	AccountService 	service;
	private	final 	AuthEntryPoint 	entry;
	private	final 	JWToken			token;

	@Autowired
	public SecurityConfiguration(AuthEntryPoint entry, JWToken token, AccountService service, AuthenticationManagerBuilder builder) throws Exception {
		this.service	= service;
		this.entry		= entry;
		this.token		= token;
		builder.userDetailsService(this.service);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/rest/**").authenticated()
		.and()
		.addFilterBefore(new TokenAuthenticationFilter(token, service), BasicAuthenticationFilter.class)
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.exceptionHandling().authenticationEntryPoint(entry)
		.and()
		.httpBasic()
		.authenticationEntryPoint(entry)
		.and().cors()
		.and().csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
		.antMatchers(HttpMethod.OPTIONS)
		.antMatchers(HttpMethod.POST, "/rest/firebase/authentication/**")
		.antMatchers(HttpMethod.POST, "/rest/authentication/**")
		.antMatchers(HttpMethod.GET, "/rest/file/**")
		.antMatchers(HttpMethod.GET, "/rest/firebase/**")
		.antMatchers("/assets/**", "/webjars/**", "/api-docs/**")
		.antMatchers("/jsondoc/**", "/jsondoc-ui.html", "/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs");
	}
}
