package com.samsung.fas.pir.login;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.samsung.fas.pir.login.auth.AuthEntryPoint;
import com.samsung.fas.pir.login.auth.JWToken;
import com.samsung.fas.pir.login.auth.TokenAuthenticationFilter;
import com.samsung.fas.pir.login.firebase.FirebaseAuthenticationFilter;
import com.samsung.fas.pir.login.rest.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;
import java.io.InputStream;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	private	final	AccountService 			service;
	private	final	AuthEntryPoint 			entry;
	private	final	JWToken 				token;
	private final 	ApplicationContext		context;

	@Autowired
	public SecurityConfiguration(AuthEntryPoint entry, JWToken token, AccountService service, AuthenticationManagerBuilder builder, ApplicationContext context) throws Exception {
		this.service	= service;
		this.entry		= entry;
		this.token		= token;
		this.context	= context;
		builder.userDetailsService(this.service);
	}

	@Override 
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/rest/**").fullyAuthenticated()
		.and()
		.addFilterBefore(new TokenAuthenticationFilter(token, service), BasicAuthenticationFilter.class)
		.addFilterBefore(new FirebaseAuthenticationFilter(), BasicAuthenticationFilter.class)
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
		.antMatchers("/assets/**", "/webjars/**", "/api-docs/**")
		.antMatchers("/jsondoc/**", "/jsondoc-ui.html");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public FirebaseAuth firebaseAuth() throws IOException {
		InputStream		input	= context.getResource("classpath:firebase-admin.json").getInputStream();
		FirebaseOptions	options	= new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(input)).setDatabaseUrl("https://pir-development.firebaseio.com/").build();
		FirebaseApp.initializeApp(options);
		return FirebaseAuth.getInstance();
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addExposedHeader("Authorization");
		config.addExposedHeader("X-Firebase-Auth");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
