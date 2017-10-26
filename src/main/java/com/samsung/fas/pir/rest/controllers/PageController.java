package com.samsung.fas.pir.rest.controllers;

import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiAuthNone;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.samsung.fas.pir.models.dto.PageDTO;
import com.samsung.fas.pir.service.PageService;

@Api(name = "Page Services", description = "Methods for listing pages", group = "Profiles", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiAuthNone
@Controller
@RequestMapping("/pages")
@Produces(MediaType.APPLICATION_JSON)
public class PageController {
	@Autowired
	private PageService pservice;

	// Get all (GET)
	@ApiMethod(description="Get all pages saved in database")
	@ApiResponseObject(clazz = PageDTO.class)
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<PageDTO>> getAll() {
		return ResponseEntity.ok(pservice.findAll());
	}

}
