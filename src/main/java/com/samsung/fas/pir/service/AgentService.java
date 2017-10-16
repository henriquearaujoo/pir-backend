package com.samsung.fas.pir.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.AgentDAO;
import com.samsung.fas.pir.models.Agent;

@Service
public class AgentService {
	private AgentDAO	agentDAO;
	
	@Autowired
	public AgentService(AgentDAO dao) {
		agentDAO = dao;
	}
	
	public boolean save(Agent agent) {
		return agentDAO.save(agent);
	}
	
	public List<Agent> findAll() {
		return agentDAO.findAll();
	}
}
