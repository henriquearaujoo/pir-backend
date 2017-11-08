package com.samsung.fas.pir.dao;

import com.samsung.fas.pir.dao.repository.IRulesRepository;
import com.samsung.fas.pir.models.entity.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RuleDAO {
	private		IRulesRepository		repository;

	@Autowired
	public RuleDAO(IRulesRepository repository) {
		this.repository = repository;
	}

	public List<Rule> findAll() {
		return repository.findAll();
	}
	
	public List<Rule> findByProfileID(UUID id) {
		return repository.findByProfileGuid(id);
	}
	
	public Rule findByProfileAndPageIDs(UUID profileID, UUID pageID) {
		return repository.findByProfileIdAndPageId(profileID, pageID);
	}
	
	public Rule save(Rule entity) {
		return repository.save(entity);
	}
	
	public Rule update(Rule entity, UUID id) {
		entity.setId(id);
		return repository.save(entity);
	}
	
	public Rule findOne(UUID id) {
		return repository.findOneByGuid(id);
	}
	
	public void delete(UUID id) {
		repository.deleteByGuid(id);
	}
}
