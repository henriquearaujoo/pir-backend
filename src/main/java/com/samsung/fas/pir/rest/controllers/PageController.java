package com.samsung.fas.pir.rest.controllers;

import com.samsung.fas.pir.rest.dto.page.RSimplePageDTO;
import com.samsung.fas.pir.rest.services.PageService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiAuthNone;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api(name = "Page Services", description = "Methods for listing pages", group = "Profiles", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiAuthNone
@Controller
@RequestMapping("/pages")
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin
public class PageController {
	private PageService pservice;

	@Autowired
	public PageController(PageService pservice) {
		this.pservice		= pservice;
	}

	// Get all (GET)
	@ApiMethod(description="Get all pages saved in database")
	@ApiResponseObject(clazz = RSimplePageDTO.class)
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<RSimplePageDTO>> getAll() {
		return ResponseEntity.ok(pservice.findAll());
	}

}
