package com.samsung.fas.pir.rest.controllers;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.dao.UserDAO;
import com.samsung.fas.pir.persistence.models.User;
import com.samsung.fas.pir.rest.controllers.base.BController;
import com.samsung.fas.pir.rest.dto.UserDTO;
import com.samsung.fas.pir.rest.services.UsersService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collection;

@Api(value = "Users", description = "REST Controller for Users", tags = "USERS")
@RequestMapping(value = "/rest/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class UserController extends BController<User, UserDTO, UserDAO> {
	@Autowired
	public UserController(UsersService service) {
		super(service);
	}

	@RequestMapping(method= RequestMethod.GET, value="/search")
	public ResponseEntity<Collection<UserDTO>> search(@QuerydslPredicate(root = User.class) Predicate predicate, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, details));
	}

	@RequestMapping(method= RequestMethod.GET, value="/search/page")
	public ResponseEntity<Page<UserDTO>> search(@QuerydslPredicate(root = User.class) Predicate predicate, Pageable pageable, @ApiIgnore @AuthenticationPrincipal UserDetails details) {
		return ResponseEntity.ok(service.findAll(predicate, pageable, details));
	}
}
