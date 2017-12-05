package com.samsung.fas.pir.dao;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.models.entity.Rule;
import com.samsung.fas.pir.repository.IRulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

	public List<Rule> findAll(Predicate predicate) {
		return StreamSupport.stream(repository.findAll(predicate).spliterator(), true).collect(Collectors.toList());
	}

	public Page<Rule> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Rule> findAll(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
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
