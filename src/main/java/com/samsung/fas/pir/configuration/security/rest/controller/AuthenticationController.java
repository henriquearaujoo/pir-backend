package com.samsung.fas.pir.configuration.security.rest.controller;

import com.samsung.fas.pir.configuration.security.auth.AuthManager;
import com.samsung.fas.pir.configuration.security.auth.JWToken;
import com.samsung.fas.pir.configuration.security.persistence.models.Account;
import com.samsung.fas.pir.configuration.security.rest.dto.AuthenticationDTO;
import com.samsung.fas.pir.configuration.security.rest.dto.ResetPasswordDTO;
import com.samsung.fas.pir.configuration.security.rest.service.AccountService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@Api(value = "Authentication", description = "REST Controller for Authentication", tags = "AUTHENTICATION")
@RequestMapping(value = "/rest/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@Validated
public class AuthenticationController {
	private	final 	JWToken 		token;
	private	final 	AuthManager	 	manager;
	private	final 	AccountService 	service;

	@Autowired
	public AuthenticationController(JWToken token, AuthManager manager, AccountService service) {
		this.token 		= token;
		this.manager 	= manager;
		this.service 	= service;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO request, Device device) throws AuthenticationException {
		try {
			Authentication 	authentication 	= manager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			HttpHeaders 	headers 		= new HttpHeaders();

			SecurityContextHolder.getContext().setAuthentication(authentication);
			headers.add(HttpHeaders.AUTHORIZATION, token.generateToken((Account) authentication.getPrincipal(), device));

			return new ResponseEntity(headers, HttpStatus.OK);
		} catch (NullPointerException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("internal.error");
		}
	}

	@RequestMapping(method = RequestMethod.POST, path = "/recover")
	public ResponseEntity recover(@Email(message = "email.invalid") @RequestParam("email") String request) {
		return ResponseEntity.ok(service.recoverPasswordByUserEmail(request));
	}

	@RequestMapping(method = RequestMethod.POST, path = "/reset")
	public ResponseEntity reset(@RequestBody @Valid ResetPasswordDTO request) {
		return ResponseEntity.ok(service.resetPassword(request));
	}
}
