package com.samsung.fas.pir.rest.controllers;

import org.jsondoc.core.annotation.ApiMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Controller
@RequestMapping("/test")
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin
public class TestController {
	@ApiMethod(description="Get all users saved in database")
	@RequestMapping(method= RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok("Teste");
	}
}
