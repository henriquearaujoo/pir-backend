package com.samsung.fas.pir.rest.api;

import java.util.UUID;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samsung.fas.pir.dto.BaseDTO;
import com.samsung.fas.pir.dto.UserDTO;
import com.samsung.fas.pir.service.UsersService;

@Controller
@RequestMapping("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserRest {
	@Autowired
	private		UsersService	uservice;
	
	// Get all users (GET)
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<BaseDTO> getAllUsers() {
		BaseDTO base = new BaseDTO();
		base.setCode(BaseDTO.Code.SUCCESS);
		base.setData(uservice.findAll());
		System.out.println(uservice.findAll().get(0).getAddress());
		return new ResponseEntity<BaseDTO>(base, HttpStatus.OK);
	}
	
	// Get specific user (GET)
	// TODO: DTO
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	@ResponseBody
	public ResponseEntity<BaseDTO> getUser(@PathVariable("id") UUID uuid) {
		BaseDTO base = new BaseDTO();
		base.setCode(BaseDTO.Code.SUCCESS);
		base.setData(uservice.findByID(uuid));
		return new ResponseEntity<BaseDTO>(base, HttpStatus.OK);
	}
	
	// Create new user (POST)
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseDTO> addUser(@RequestBody UserDTO user) {
		BaseDTO base = new BaseDTO();
		base.setCode(BaseDTO.Code.SUCCESS);
		base.setData(uservice.save(user));
		return new ResponseEntity<BaseDTO>(base, HttpStatus.OK);
	}
	
	// Update user (PUT)
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	public @ResponseBody ResponseEntity<Object> updateUser(@PathVariable("id") UUID uuid, @RequestBody UserDTO user) {
		return new ResponseEntity<Object>(uservice.updateUser(user, uuid), HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
    public void handleException(Exception e) {
        e.printStackTrace();
    }
}
