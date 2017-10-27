package com.samsung.fas.pir.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsung.fas.pir.dao.repository.IRulesRepository;
import com.samsung.fas.pir.models.entity.Rule;

@Service
public class RuleDAO {
	@Autowired
	private		IRulesRepository		repository;

	public List<Rule> findAll() {
		return repository.findAll();
	}
	
	public List<Rule> findByProfileID(UUID id) {
		return repository.findByProfileId(id);
	}
	
	public Rule findByProfileAndPageIDs(UUID profileID, UUID pageID) {
		return repository.findByProfileAndPageIDs(profileID, pageID);
	}
	
	public Rule save(Rule entity) {
		return repository.save(entity);
	}
	
	public Rule update(Rule entity, UUID id) {
		entity.setId(id);
		return repository.save(entity);
	}
	
	public Rule findOne(UUID id) {
		return repository.findOne(id);
	}
	
	public void delete(UUID id) {
		repository.delete(id);
	}
}
