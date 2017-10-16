package com.samsung.fas.pir.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.repository.IAgentRepository;
import com.samsung.fas.pir.models.Agent;

/*
 * This is an example class that will use
 * JPA Repository Interface mixed with some 
 * native SQL queries when needed (like a facade pattern)
 */
@Service
public class AgentDAO {
	private		IAgentRepository		repository;
	
	@Autowired
	public AgentDAO(IAgentRepository repository) {
		this.repository = repository;
	}
	
	public boolean queryCreate(Agent agent) {
		return repository.save(agent) != null;
	}
	
	public List<Agent> queryAll() {
		return repository.findAll();
	}

}
