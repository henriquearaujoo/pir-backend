package com.samsung.fas.pir.rest.api;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.samsung.fas.pir.dto.NewUserDTO;
import com.samsung.fas.pir.dto.UserListDTO;
import com.samsung.fas.pir.models.User;
import com.samsung.fas.pir.service.UsersService;

@Controller
@RequestMapping("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserRest {
	@Autowired
	private UsersService uservice;
	
	// Get all users (GET)
	// TODO: DTO
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<User>> get() {
		return new ResponseEntity<List<User>>(uservice.findAll(), HttpStatus.OK);
	}
	
	// Get specific user (GET)
	// TODO: DTO
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public @ResponseBody ResponseEntity<User> get(@PathVariable("id") UUID uuid) {
		System.out.println(uuid);
		return new ResponseEntity<User>(uservice.findByID(uuid), HttpStatus.OK);
	}
	
	// Create new user (POST)
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> addAgent(@RequestBody NewUserDTO user) {
		try {
			return new ResponseEntity<Object>(uservice.save(user.getModel()), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
