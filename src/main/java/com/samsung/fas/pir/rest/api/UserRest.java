package com.samsung.fas.pir.rest.api;

import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.bytecode.buildtime.spi.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.samsung.fas.pir.dto.UsersDTO;
import com.samsung.fas.pir.models.User;
import com.samsung.fas.pir.service.UsersService;

@Controller
@RequestMapping("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserRest {
	@Autowired
	private UsersService uservice;
	
	// Get all users (GET)
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<User>> test() {
		return new ResponseEntity<List<User>>(uservice.findAll(), HttpStatus.OK);
	}
	
	// Create new user (POST)
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Boolean> addAgent(@RequestBody UsersDTO agent) {
//		java.util.logging.Logger
		//uservice.save(agent.getModel());
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
}
