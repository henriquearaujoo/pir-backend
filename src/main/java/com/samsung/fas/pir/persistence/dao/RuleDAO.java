package com.samsung.fas.pir.persistence.dao;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.entity.Rule;
import com.samsung.fas.pir.persistence.repository.IRulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
	
	public Rule findByProfileAndPageIDs(long profileID, UUID pageID) {
		return repository.findByProfileIdAndPageId(profileID, pageID);
	}
	
	public Rule save(Rule entity) {
		return repository.save(entity);
	}

	public List<Rule> save(Set<Rule> entities) {
		List<Rule>	persisted	= new ArrayList<>();
		for (Rule rule : entities) {
			persisted.add(repository.save(rule));
		}
		return persisted;
	}
	
	public Rule update(Rule entity, long id) {
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
