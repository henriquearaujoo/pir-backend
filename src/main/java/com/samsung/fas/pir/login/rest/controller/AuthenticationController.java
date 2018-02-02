package com.samsung.fas.pir.login.rest.controller;

import com.samsung.fas.pir.login.auth.AuthManager;
import com.samsung.fas.pir.login.auth.JWToken;
import com.samsung.fas.pir.login.persistence.models.entity.Account;
import com.samsung.fas.pir.login.providers.DeviceProvider;
import com.samsung.fas.pir.login.rest.dto.AuthenticationDTO;
import com.samsung.fas.pir.login.rest.dto.ResetPasswordDTO;
import com.samsung.fas.pir.login.rest.service.AccountService;
import org.hibernate.validator.constraints.Email;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiAuthNone;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Api(name = "Authentication Services", description = "Methods for managing authentication", group = "Authentication", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiAuthNone
@Controller
@RequestMapping("/authentication")
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.POST }, exposedHeaders = HttpHeaders.AUTHORIZATION)
public class AuthenticationController {
	@Autowired	private 	JWToken				token;
	@Autowired 	private 	AuthManager 		manager;
	@Autowired 	private 	AccountService 		service;
	@Autowired 	private 	DeviceProvider		provider;

	@RequestMapping(method = RequestMethod.POST, path = "/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO request, Device device) throws AuthenticationException {
		try {
			Authentication	authentication 	= manager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
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
	public ResponseEntity recover(@RequestParam("email") @Email String request) {
		return ResponseEntity.ok(service.recoverPasswordByUserEmail(request));
	}

	@RequestMapping(method = RequestMethod.POST, path = "/reset")
	public ResponseEntity reset(@RequestBody @Valid ResetPasswordDTO request) {
		return ResponseEntity.ok(service.resetPassword(request));
	}

//	@RequestMapping(method = RequestMethod.POST, path = "/refresh")
//	public ResponseEntity refresh(HttpServletRequest request, Principal principal) {
//		String			authToken	= token.getToken(request);
//		Device			device		= provider.getCurrentDevice(request);
//		HttpHeaders		headers		= new HttpHeaders();
//
//		if (authToken != null && principal != null) {
//			// TODO check user password last update
//			headers.add(HttpHeaders.AUTHORIZATION, token.refreshToken(authToken, device));
//			// Client already have type and id
//			return new ResponseEntity(headers, HttpStatus.OK);
//		} else {
//			headers.add(HttpHeaders.AUTHORIZATION, authToken);
//			return ResponseEntity.ok(null);
//		}
//	}

//	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
//	@PreAuthorize("hasRole('USER')")
//	public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
//		userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
//		Map<String, String> result = new HashMap<>();
//		result.put( "result", "success" );
//		return ResponseEntity.accepted().body(result);
//	}
//
//	static class PasswordChanger {
//		public String oldPassword;
//		public String newPassword;
//	}

}
