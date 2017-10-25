package com.samsung.fas.pir.rest.controllers;

import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samsung.fas.pir.dto.PageDTO;
import com.samsung.fas.pir.service.PageService;

@Controller
@RequestMapping("/pages")
@Produces(MediaType.APPLICATION_JSON)
public class PageController {
	@Autowired
	private PageService pservice;

	// Get all (GET)
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<PageDTO>> getAll() {
		return ResponseEntity.ok(pservice.findAll());
	}

}
