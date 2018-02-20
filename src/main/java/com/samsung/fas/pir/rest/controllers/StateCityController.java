package com.samsung.fas.pir.rest.controllers;

import com.samsung.fas.pir.rest.dto.address.CityDTO;
import com.samsung.fas.pir.rest.dto.address.StateDTO;
import com.samsung.fas.pir.rest.services.StateCityService;
import org.jsondoc.core.annotation.*;
import org.jsondoc.core.pojo.ApiStage;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api(name = "State City Services", description = "Methods for getting state and cities", group = "Address", visibility = ApiVisibility.PUBLIC, stage = ApiStage.BETA)
@ApiAuthNone
@Controller
@RequestMapping("/rest/states")
@Produces(MediaType.APPLICATION_JSON)
public class StateCityController {
	private		StateCityService	scservice;

	@Autowired
	public StateCityController(StateCityService scservice) {
		this.scservice		= scservice;
	}
	
	@ApiMethod(description="Get list of states")
	@ApiResponseObject(clazz = StateDTO.class)
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<StateDTO>> getAll() {
		return ResponseEntity.ok(scservice.findAllStates());
	}
	
	@ApiMethod(description="Get specific state info")
	@ApiResponseObject(clazz = StateDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="/state/{id}")
	@ResponseBody
	public ResponseEntity<StateDTO> get(@ApiPathParam @PathVariable("id") long id) {
		return ResponseEntity.ok(scservice.findOneState(id));
	}

	@ApiMethod(description="Get specific state cities")
	@ApiResponseObject(clazz = CityDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="/state/{id}/cities")
	@ResponseBody
	public ResponseEntity<List<CityDTO>> getCitiesByState(@ApiPathParam @PathVariable("id") long id) {
		return ResponseEntity.ok(scservice.findAllCitiesByState(id));
	}

	@ApiMethod(description="Get specific city info")
	@ApiResponseObject(clazz = CityDTO.class)
	@RequestMapping(method=RequestMethod.GET, value="/city/{id}")
	@ResponseBody
	public ResponseEntity<CityDTO> getCity(@ApiPathParam @PathVariable("id") long id) {
		return ResponseEntity.ok(scservice.findOneCity(id));
	}
}
