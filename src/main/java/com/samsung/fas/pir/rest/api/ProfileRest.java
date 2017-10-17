package com.samsung.fas.pir.rest.api;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.samsung.fas.pir.service.ProfileService;

@Controller
@RequestMapping("/profiles")
@Produces(MediaType.APPLICATION_JSON)
public class ProfileRest {
	@Autowired
	private ProfileService pservice;

}
