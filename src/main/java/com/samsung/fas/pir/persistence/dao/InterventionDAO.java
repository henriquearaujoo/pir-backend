package com.samsung.fas.pir.persistence.dao;

import com.querydsl.core.types.Predicate;
import com.samsung.fas.pir.persistence.models.entity.Intervention;
import com.samsung.fas.pir.persistence.repository.IInterventionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class InterventionDAO {
	private IInterventionRepository repository;

	@Autowired
	public InterventionDAO(IInterventionRepository repository) {
		this.repository = repository;
	}

	public Intervention findOne(long id) {
		return repository.findOne(id);
	}

	public List<Intervention> findAll() {
		return repository.findAll();
	}

	public List<Intervention> findAll(Predicate predicate) {
		return StreamSupport.stream(repository.findAll(predicate).spliterator(),true).collect(Collectors.toList());
	}

	public Page<Intervention> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Intervention> findAll(Predicate predicate, Pageable pageable) {
		return repository.findAll(predicate, pageable);
	}

	public Intervention save(Intervention user) {
		return repository.save(user);
	}

	public void delete(long id) {
		repository.delete(id);
	}
}
