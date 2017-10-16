package com.samsung.fas.pir.rest.api;

import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import com.samsung.fas.pir.dao.AgentDAO;
import com.samsung.fas.pir.dto.AgentDTO;
import com.samsung.fas.pir.models.Agent;
import com.samsung.fas.pir.service.AgentService;

@Controller
@RequestMapping("/agents")
@Produces(MediaType.APPLICATION_JSON)
public class AgentREST {
	// TODO: BOs
	@Autowired
	private			AgentService		agentdb;
	
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Agent>> test() {
		return new ResponseEntity<List<Agent>>(agentdb.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Boolean> addAgent(@RequestBody AgentDTO agent) {	
		agentdb.save(agent.getModel());
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
}
