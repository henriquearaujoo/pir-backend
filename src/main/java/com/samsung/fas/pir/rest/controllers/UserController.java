package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.User;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.UserDTO;
import com.samsung.fas.pir.rest.services.UserBO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collection;
import java.util.UUID;

@Api(value = "Users", description = "REST Controller for Users", tags = "USERS")
@RequestMapping(value = "/rest/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class UserController extends BController<UserBO, UserDTO> {
	@Autowired
	public UserController(UserBO service) {
		super(service);
	}

	@RequestMapping(method= RequestMethod.PUT, value="/enable/{id}")
	public ResponseEntity<UserDTO> enable(@PathVariable("id") UUID id, Device device) {
		return ResponseEntity.ok(getService().enable(id, device));
	}

	@RequestMapping(method= RequestMethod.GET, value="/agents")
	public ResponseEntity<Collection<UserDTO>> searchAgents(Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAllAgents(device, details));
	}

	@RequestMapping(method= RequestMethod.GET, value="/agents/search")
	public ResponseEntity<Collection<UserDTO>> searchAgents(@QuerydslPredicate(root = User.class) Predicate predicate, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAllAgents(predicate, device, details));
	}

	@RequestMapping(method= RequestMethod.GET, value="/agents/search/page")
	public ResponseEntity<Page<UserDTO>> searchAgents(@QuerydslPredicate(root = User.class) Predicate predicate, Pageable pageable, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAllAgents(predicate, pageable, device, details));
	}

	@RequestMapping(method= RequestMethod.GET, value="/search")
	public ResponseEntity<Collection<UserDTO>> search(@QuerydslPredicate(root = User.class) Predicate predicate, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, device, details));
	}

	@RequestMapping(method= RequestMethod.GET, value="/search/page")
	public ResponseEntity<Page<UserDTO>> search(@QuerydslPredicate(root = User.class) Predicate predicate, Pageable pageable, Device device, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(getService().findAll(predicate, pageable, device, details));
	}
}
